package ru.afrolovskiy.messageSystem;

import ru.afrolovskiy.base.Abonent;
import ru.afrolovskiy.base.AccountService;
import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.base.Msg;

public abstract class MsgToAS extends Msg {
	public MsgToAS(Address from, Address to) {
		super(from, to);
	}
	
	public void exec(Abonent abonent) {
		if (abonent instanceof AccountService) {
			exec((AccountService) abonent);
		}
	}
	
	public abstract void exec(AccountService accountService);
}
