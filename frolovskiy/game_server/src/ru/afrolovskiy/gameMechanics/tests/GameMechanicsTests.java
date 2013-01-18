package ru.afrolovskiy.gameMechanics.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.junit.Test;

import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.base.Field;
import ru.afrolovskiy.base.Frontend;
import ru.afrolovskiy.base.GameMechanics;
import ru.afrolovskiy.base.GameSession;
import ru.afrolovskiy.base.GameState;
import ru.afrolovskiy.base.PointState;
import ru.afrolovskiy.base.User;
import ru.afrolovskiy.base.UserSession;
import ru.afrolovskiy.gameMechanics.GameMechanicsImpl;

public class GameMechanicsTests {
	private int userId1 = 0;
	private int userId2 = 1;
	
	@Test
	public void testStartGameSession() {
		Frontend frontend = new FrontendMock();  
		GameMechanics gameMechanics = new GameMechanicsImpl(frontend);

		gameMechanics.startGameSession(userId1, userId2);

		GameSession gameSession1 = gameMechanics.getGameSession(userId1);
		GameSession gameSession2 = gameMechanics.getGameSession(userId2);		
		assertEquals(gameSession1, gameSession2);
		assertEquals(gameSession1.getUserId1(), userId1);
		assertEquals(gameSession1.getUserId2(), userId2);
		assertEquals(gameSession1.getGameState(), GameState.Play);
		assertEquals(gameSession1.getCurrentStepUserId(), userId1);
		assertEquals(gameSession1.getUserPointState(userId1), PointState.BLUE);
		assertEquals(gameSession1.getUserPointState(userId2), PointState.RED);
	}
	
	@Test
	public void testReplicateGamesToFrontend() {
		Frontend frontend = new FrontendMock();  
		GameMechanics gameMechanics = new GameMechanicsImpl(frontend);
		gameMechanics.startGameSession(userId1, userId2);
		gameMechanics.replicateGamesToFrontend();
		
		UserSession userSession1 = frontend.getUserSessionByUserId(userId1);
		GameSession gameSession1 = gameMechanics.getGameSession(userId1);
		assertEquals(userSession1.getField(), gameSession1.getField());
		assertEquals(userSession1.getColor(), gameSession1.getUserPointState(userId1).toString());
		assertEquals(userSession1.isMyTurn(), true);
		
		
		UserSession userSession2 = frontend.getUserSessionByUserId(userId2);
		GameSession gameSession2 = gameMechanics.getGameSession(userId2);
		assertEquals(userSession2.getField(), gameSession2.getField());
		assertEquals(userSession2.getColor(), gameSession2.getUserPointState(userId2).toString());
		assertEquals(userSession2.isMyTurn(), false);
	}
	
	@Test
	public void testAddDot() {
		Frontend frontend = new FrontendMock();  
		GameMechanics gameMechanics = new GameMechanicsImpl(frontend);
		gameMechanics.startGameSession(userId1, userId2);
		GameSession gameSession = gameMechanics.getGameSession(userId1);
		Field field = new Field(50, 50);
		
		gameMechanics.addDot(userId1, 0, 0);
		field.setPointState(0, 0, PointState.BLUE);
		assertEquals(gameSession.getField(), field);
				
		gameMechanics.addDot(userId1, 0, 1);
		assertEquals(gameSession.getField(), field);
		
		gameMechanics.addDot(userId2, 0, 1);
		field.setPointState(0, 1, PointState.RED);
		assertEquals(gameSession.getField(), field);
		
		gameMechanics.addDot(userId1, 0, 1);
		assertEquals(gameSession.getField(), field);
		
		gameMechanics.addDot(userId1, 0, 2);
		field.setPointState(0, 2, PointState.BLUE);
		assertEquals(gameSession.getField(), field);
	}

}

class FrontendMock implements Frontend {
	private Map<Integer, UserSession> userIdToUserSession;
	
	public FrontendMock() {
		userIdToUserSession = new HashMap<Integer, UserSession>();
		
		int userId1 = 0;
		User user1 = new User("Alexey", userId1);
		UserSession session1 = new UserSession();
		session1.setUser(user1);
		userIdToUserSession.put(userId1, session1);
		
		int userId2 = 1;
		User user2 = new User("Elena", userId2);
		UserSession session2 = new UserSession();
		session2.setUser(user2);
		userIdToUserSession.put(userId2, session2);		
	}
	
	public void run() {}

	public void updateUser(int sessionId, int userId, String userName) {}

	public Address getAddress() { return null; }

	public void handleRequest(Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) { }

	public UserSession getUserSession(int sessionId) { return null; }

	public Integer getSessionIdByUserId(int userId) { return null; }

	public UserSession getUserSessionByUserId(int userId) {
		return userIdToUserSession.get(userId);
	}	
	
}
