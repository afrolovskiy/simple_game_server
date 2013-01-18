package ru.afrolovskiy.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import ru.afrolovskiy.addressService.AddressServiceImpl;
import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.base.AddressService;
import ru.afrolovskiy.base.Frontend;
import ru.afrolovskiy.base.MessageSystem;
import ru.afrolovskiy.base.Msg;
import ru.afrolovskiy.base.User;
import ru.afrolovskiy.base.UserSession;
import ru.afrolovskiy.base.VFS;
import ru.afrolovskiy.messageSystem.MessageSystemImpl;
import ru.afrolovskiy.page.AuthPageGenerator;
import ru.afrolovskiy.page.GamePageGenerator;
import ru.afrolovskiy.page.WaitPageGenerator;
import ru.afrolovskiy.utils.TimeHelper;
import ru.afrolovskiy.vfs.VFSImpl;

public class FrontendImpl implements Frontend {
	private MessageSystem messageSystem;
	private AddressService addressService;
	private Address address;
	private Map<Integer, UserSession> sessionIdToUserSession;
	private Map<Integer, Integer> userIdToSessionId;
	private List<Integer> freeUserSessions;
	private VFS vfs;
	
	private AuthPageGenerator authPageGenerator;
	private WaitPageGenerator waitPageGenerator;
	private GamePageGenerator gamePageGenerator;
	
	private int TICK_TIME = 1000;
	
	public FrontendImpl() {
		this.address = new Address();
		this.addressService = AddressServiceImpl.getInstance();
		this.messageSystem = MessageSystemImpl.getInstance();
		this.messageSystem.registrateAbonent("Frontend", this);
		this.sessionIdToUserSession = new HashMap<Integer, UserSession>();
		this.userIdToSessionId = new HashMap<Integer, Integer>(); 
		this.freeUserSessions = new LinkedList<Integer>();
		initializePageGenerators();
		vfs = new VFSImpl("");
	}
		
	private void initializePageGenerators() {
		this.authPageGenerator = new AuthPageGenerator();
		this.waitPageGenerator = new WaitPageGenerator();
		this.gamePageGenerator = new GamePageGenerator();
	}
	
	public Address getAddress() {
		return this.address;
	}
	
	public void run() {
		while (true) {
			messageSystem.execForAbonent(this);
			TimeHelper.sleep(TICK_TIME);
		}
	}
	
	public void handleRequest(Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) {		
		if (isStaticContentRequest(baseRequest, request)) {
			handleStaticContentRequest(baseRequest, request, response);
		} else if (isRefreshGameRequest(baseRequest, request)) {
			handleRefreshGameRequest(baseRequest, request, response);
		} else {
			handleGameEventsRequest(baseRequest, request, response);
		}	
	}
	
	private boolean isStaticContentRequest(Request baseRequest, HttpServletRequest request) {
		if (request.getMethod().equals("GET") && request.getRequestURI().startsWith("/static"))
			return true;
		return false;
	}
	
	private void handleStaticContentRequest(Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) {
		int filePathStartIndex = request.getRequestURI().indexOf("/static");
		if (filePathStartIndex >= 0) {			
			try {
				String filePath = request.getRequestURI().substring(filePathStartIndex + 1);
				byte[] outBuff = vfs.getBytes(filePath);
				response.getOutputStream().write(outBuff, 0, outBuff.length);
				response.setContentType("image/png;charset=utf-8");
				response.setStatus(HttpServletResponse.SC_OK);				
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}			
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		baseRequest.setHandled(true);
	}
	
	private boolean isRefreshGameRequest(Request baseRequest, HttpServletRequest request) {
		if (request.getMethod().equals("GET") && request.getRequestURI().startsWith("/refresh"))
			return true;
		return false;
	}
	
	private void handleRefreshGameRequest(Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) {
		String sessionId = request.getParameter("sessionId");
		System.out.println("sessionId = " + sessionId);
		if (sessionId != null) {
			String clickedDotX = request.getParameter("clickedDotX");
			String clickedDotY = request.getParameter("clickedDotY");
			System.out.println("Clicked dot: " + clickedDotX + ", " + clickedDotY);
			if (clickedDotX != null && clickedDotY != null) {
				sendClickedPointToGameMechanics(new Integer(clickedDotX), 
						new Integer(clickedDotY), new Integer(sessionId));				
			}						
			buildRefreshGameResponse(response, new Integer(sessionId));				
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);	
		}
		baseRequest.setHandled(true);
	}
	
	private void sendClickedPointToGameMechanics(int clickedDotX, int clickedDotY, int sessionId) {		
		MsgAddNewDot msg = new MsgAddNewDot(
				this.getAddress(), addressService.getAbonentAddress("GameMechanics"),
				getUserSession(sessionId).getUser().getId(), clickedDotX, clickedDotY);
		messageSystem.sendMessage(msg);
	}
	
	private void buildRefreshGameResponse(HttpServletResponse response, int sessionId) {			
		UserSession userSession = sessionIdToUserSession.get(sessionId);
		if (userSession != null) {
			try {
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				Boolean myTurn = userSession.isMyTurn();
				String field = userSession.getField().toJSON();
				String s =  "handle({\"myTurn\": " + myTurn + ", \"hasChanges\": " + "true" + 
							", \"field\": " + field + "});";
				out.println(s);		
				response.setStatus(HttpServletResponse.SC_OK);
			} catch(IOException err) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
			}
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}		
	}
	
	private void handleGameEventsRequest(Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";		
		Integer sessionId = getSessionId(baseRequest);
		if (sessionId == null) {
			sessionId = registrateUserSession();
			result = authPageGenerator.getPage(baseRequest, request, sessionId);
		} else {
			Integer userId = getUserId(sessionId);
			if (userId == null) {
				String userName = baseRequest.getParameter("user_name");
				if (userName != null) {
					sendMessageToAS(sessionId, userName);					
				} 
				result = waitPageGenerator.getPage(baseRequest, request, sessionId);
			} else {
				if (!freeUserSessions.contains(sessionId)){ 
					UserSession userSession = sessionIdToUserSession.get(sessionId);
					if (userSession != null) {
						result = gamePageGenerator.getPage(baseRequest, request, userSession);
					} else {
						result = waitPageGenerator.getPage(baseRequest, request, sessionId);
					}
				} else {
					if (freeUserSessions.size() >= 2) {
						startNewGameSession(sessionId);
					}
					result = waitPageGenerator.getPage(baseRequest, request, sessionId);
				}				
			}		
		}
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println(result);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}	
		baseRequest.setHandled(true);
	}
	
	private Integer registrateUserSession() {
		UserSession session = new UserSession();
		int sessionId = session.getSessionId();
		sessionIdToUserSession.put(sessionId, session);
		return sessionId;
	}
	
	private Integer getSessionId(Request baseRequest) {
		String sessionId = baseRequest.getParameter("sessionId");
		if (sessionId == null) {
			return null;
		}
		return new Integer(sessionId);		
	}
	
	private Integer getUserId(Integer sessionId) {
		UserSession session = sessionIdToUserSession.get(sessionId);
		User user = session.getUser();
		if (user != null && user.getId() != null)
			return user.getId();
		return null;		
	}
	
	private void sendMessageToAS(Integer sessionId, String userName) {
		Msg message = new MsgGetUserId(this.getAddress(),
				addressService.getAbonentAddress("AccountService"), 
				sessionId, userName);
		messageSystem.sendMessage(message);
	}
	
	private void startNewGameSession(Integer sessionId1) {
		freeUserSessions.remove(sessionId1);
		Integer sessionId2 = freeUserSessions.get(0);
		freeUserSessions.remove(sessionId2);
		MsgStartGameSession msg = new MsgStartGameSession(
				this.getAddress(), addressService.getAbonentAddress("GameMechanics"), 
				getUserIdBySessionId(sessionId1), getUserIdBySessionId(sessionId2));
		messageSystem.sendMessage(msg);
	}
		
	public void updateUser(int sessionId, int userId, String userName) {
		UserSession session = sessionIdToUserSession.get(sessionId);
		if (session.getUser() == null) {
			session.setUser(new User(userName, userId));
			userIdToSessionId.put(userId, sessionId);
		}
		freeUserSessions.add(sessionId);
	}
	
	public UserSession getUserSession(int sessionId) {
		return sessionIdToUserSession.get(sessionId);
	}
	
	public Integer getSessionIdByUserId(int userId) {
		return userIdToSessionId.get(userId);
	}
	
	public UserSession getUserSessionByUserId(int userId) {
		Integer sessionId = getSessionIdByUserId(userId);
		if (sessionId == null) {
			return null;
		}
		return getUserSession(sessionId);
	}
	
	public Integer getUserIdBySessionId(Integer sessionId) {
		return getUserSession(sessionId).getUser().getId();		
	}
	

}
