package ru.afrolovskiy.base.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.afrolovskiy.base.Address;

public class AddressTests {

	@Test
	public void test() {
		Address address1 = new Address();
		Address address2 = new Address();
		assertEquals(address2.getAbonentId() - address1.getAbonentId(), 1);
	}

}
