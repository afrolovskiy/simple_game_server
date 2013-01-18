package ru.afrolovskiy.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

public interface Frontend extends Abonent, Runnable {
	public void updateUser(int sessionId, int userId, String userName);
	public Address getAddress();
	public void handleRequest(Request baseRequest, HttpServletRequest request, HttpServletResponse response); 
	public UserSession getUserSession(int sessionId);
	public Integer getSessionIdByUserId(int userId);
	public UserSession getUserSessionByUserId(int userId);
}
