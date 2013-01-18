package ru.afrolovskiy.frontend;

import ru.afrolovskiy.base.AccountService;
import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.messageSystem.MsgToAS;

public class MsgGetUserId extends MsgToAS {
	private final int sessionId;
	private final String userName;
	
	public MsgGetUserId(Address from, Address to, int sessionId, String userName) {
		super(from, to);
		this.sessionId = sessionId;
		this.userName = userName;
	}

	public void exec(AccountService accountService) {
		accountService.getUserId(sessionId, userName);
	}	
}

