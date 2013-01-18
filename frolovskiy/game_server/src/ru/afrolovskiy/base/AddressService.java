package ru.afrolovskiy.base;

public interface AddressService {
	public void registrateAbonent(String abonentName, Abonent abonent);
	public Address getAbonentAddress(String abonentName);
}
