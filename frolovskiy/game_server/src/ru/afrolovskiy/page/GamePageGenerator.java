package ru.afrolovskiy.page;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.server.Request;

import ru.afrolovskiy.base.User;
import ru.afrolovskiy.base.UserSession;

public class GamePageGenerator extends PageGenerator {

	public String getPage(Request baseRequest, HttpServletRequest request, 	Object... args) {
		UserSession userSession = (UserSession) args[0];  
		Integer sessionId = userSession.getSessionId();
		User user = userSession.getUser();
		String userColor = userSession.getColor();
		Boolean myTurn = userSession.isMyTurn();
		StringBuilder buff = new StringBuilder();
		buff.append("<body>\n");
		
		buff.append("<p id=\"turn\">Turn</p>\n");
		
		buff.append("<canvas id=\"field\" height=\"500\" width=\"500\" align=\"center\" style=\"border:1px solid blue;\">\n");
		buff.append("	Html5 canvas is not supported\n");
		buff.append("</canvas>\n");
		
		buff.append("<div style=\"position: absolute; left: -100px;\">\n");
		buff.append("<img id=\"dot-red\" src=\"static/dot-red.png\">\n");
		buff.append("<img id=\"dot-blue\" src=\"static/dot-blue.png\">\n");
		buff.append("<img id=\"dot-red-transparent\" src=\"static/dot-red-transparent.png\">\n");
		buff.append("<img id=\"dot-blue-transparent\" src=\"static/dot-blue-transparent.png\">\n");
		buff.append("</div>\n");
		
		buff.append("<form id=\"main_form\" method=\"POST\">\n");
		buff.append("	<input id=\"userId\" type=\"hidden\" name=\"userId\" value=" + user.getId() + ">\n");
		buff.append("	<input id=\"sessionId\" type=\"hidden\" name=\"sessionId\" value=" + sessionId + ">\n");
		buff.append("	<input id=\"myTurn\" type=\"hidden\" name=\"myTurn\" value=" + myTurn + ">\n");
		buff.append("	<input id=\"userColor\" type=\"hidden\" name=\"userColor\" value=" + userColor + ">\n");
		buff.append("</form>\n");
		
		buff.append("<script language=\"JavaScript\" type=\"text/javascript\" src=\"static/jquery-1.8.2.min.js\"></script>\n");
		buff.append("<script src=\"static/dots.js\"></script>\n");
				
		buff.append("</body>\n");
		return buff.toString();
	}
}


