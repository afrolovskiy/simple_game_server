package ru.afrolovskiy.messageSystem;

import ru.afrolovskiy.base.Abonent;
import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.base.Frontend;
import ru.afrolovskiy.base.Msg;

public abstract class MsgToFrontend extends Msg{
	public MsgToFrontend(Address from, Address to) {
		super(from, to);
	}
	
	public void exec(Abonent abonent) {
		if (abonent instanceof Frontend) {
			exec((Frontend) abonent);
		}
	}
	
	public abstract void exec(Frontend frontend);
}