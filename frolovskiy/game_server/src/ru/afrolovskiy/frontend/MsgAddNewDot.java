package ru.afrolovskiy.frontend;

import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.base.GameMechanics;
import ru.afrolovskiy.messageSystem.MsgToGM;

public class MsgAddNewDot extends MsgToGM {
	private final int sessionId; 
	private final int i;
	private final int j;
	
	public MsgAddNewDot(Address from, Address to, int sessionId, int i, int j) {
		super(from, to);
		this.sessionId = sessionId;
		this.i = i;
		this.j = j;		
	}
	
	public int getSessionId() {
		return this.sessionId;
	}
	
	public int getI() {
		return this.i;
	}
	
	public int getJ() {
		return this.j;
	}

	public void exec(GameMechanics gameMechanics) {
		gameMechanics.addDot(sessionId, i, j);		
	}

}
