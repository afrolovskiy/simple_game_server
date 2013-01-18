package ru.afrolovskiy.frontend;

import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.base.Frontend;
import ru.afrolovskiy.messageSystem.MsgToFrontend;

public class MsgUpdateUserId extends MsgToFrontend {
	private final int sessionId;
	private final int userId;
	private final String userName;
	
	public MsgUpdateUserId(Address from, Address to, int sessionId, int userId, String userName) {
		super(from, to);
		this.userId = userId;
		this.sessionId = sessionId;
		this.userName = userName;
	}
	
	public void exec(Frontend frontend) {
		frontend.updateUser(sessionId, userId, userName);
	}
}

