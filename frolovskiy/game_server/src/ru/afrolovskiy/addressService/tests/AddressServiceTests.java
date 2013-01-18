package ru.afrolovskiy.addressService.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.afrolovskiy.addressService.AddressServiceImpl;
import ru.afrolovskiy.base.Abonent;
import ru.afrolovskiy.base.Address;
import ru.afrolovskiy.base.AddressService;

public class AddressServiceTests {

	@Test
	public void test() {
		AddressService service = AddressServiceImpl.getInstance();
		Abonent ab1 = new SimpleAbonent();
		service.registrateAbonent("abonent1", ab1);
		Abonent ab2 = new SimpleAbonent();
		service.registrateAbonent("abonent2", ab2);
		assertEquals(service.getAbonentAddress("abonent1"), ab1.getAddress());
		assertEquals(service.getAbonentAddress("abonent2"), ab2.getAddress());
		assertNull(service.getAbonentAddress("abonent3"));
	}

}

class SimpleAbonent implements Abonent {
	private Address address;
	
	public SimpleAbonent() {
		address = new Address();
	}
	public Address getAddress() {
		return address;
	}		
}
