package ru.afrolovskiy.accountService;

import ru.afrolovskiy.addressService.AddressServiceImpl;
import ru.afrolovskiy.base.AccountService;
import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.base.AddressService;
import ru.afrolovskiy.base.MessageSystem;
import ru.afrolovskiy.base.Msg;
import ru.afrolovskiy.base.Users;
import ru.afrolovskiy.frontend.MsgUpdateUserId;
import ru.afrolovskiy.messageSystem.MessageSystemImpl;

public class AccountServiceImpl implements AccountService {
	private MessageSystem messageSystem;
	private AddressService addressService;
	private int TICK_TIME = 1000;
	private Address address;
	private Users users;
	
	public AccountServiceImpl(Users users) {
		this.address = new Address();
		this.addressService = AddressServiceImpl.getInstance();
		this.users = users;
		initializeMessageSystem();
	}
	
	private void initializeMessageSystem() {
		this.messageSystem = MessageSystemImpl.getInstance();
		this.messageSystem.registrateAbonent("AccountService", this);
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void run() {
		while (true) {
			messageSystem.execForAbonent(this);
			try {
				Thread.sleep(TICK_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public void getUserId(int sessionId, String userName) {
		Integer userId = users.getId(userName);
		if (userId != null) {
			Address addressFrom = this.getAddress();
			Address addressTo = addressService.getAbonentAddress("Frontend");
			Msg message = new MsgUpdateUserId(addressFrom, addressTo, sessionId, userId, userName);
			messageSystem.sendMessage(message);
		}
	}
}
