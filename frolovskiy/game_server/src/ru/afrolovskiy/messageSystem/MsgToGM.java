package ru.afrolovskiy.messageSystem;

import ru.afrolovskiy.base.Abonent;
import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.base.GameMechanics;
import ru.afrolovskiy.base.Msg;

public abstract class MsgToGM extends Msg {

	public MsgToGM(Address from, Address to) {
		super(from, to);
	}

	public void exec(Abonent abonent) {
		if (abonent instanceof GameMechanics) {
			exec((GameMechanics) abonent);
		}
	}
	
	public abstract void exec(GameMechanics gameMechanics);
}
