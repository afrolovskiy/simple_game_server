package ru.afrolovskiy.base;

public interface MessageSystem {
	public void registrateAbonent(String abonentName, Abonent abonent);
	public void sendMessage(Msg message);
	public void execForAbonent(Abonent abonent);
}
