package ru.afrolovskiy.base.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.afrolovskiy.base.User;

public class UserTests {

	@Test
	public void test() {
		User user = new User("Alexey", 0);
		assertEquals(user.getId(), new Integer(0));
		assertEquals(user.getName(), "Alexey");		
	}

}
