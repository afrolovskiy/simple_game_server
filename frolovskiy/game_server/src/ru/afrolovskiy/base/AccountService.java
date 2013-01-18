package ru.afrolovskiy.base;

public interface AccountService extends Abonent, Runnable{
	public Address getAddress();
	public void getUserId(int sessionId, String userName);
}