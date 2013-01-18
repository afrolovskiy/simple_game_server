package ru.afrolovskiy.base.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.afrolovskiy.base.Field;
import ru.afrolovskiy.base.PointState;
import ru.afrolovskiy.base.User;
import ru.afrolovskiy.base.UserSession;

public class UserSessionTests {

	@Test
	public void testInitialization() {
		UserSession session1 = new UserSession();
		UserSession session2 = new UserSession();
		assertEquals(session2.getSessionId() - session1.getSessionId(), 1);
	}
	
	@Test
	public void testUserSetting() {
		UserSession session = new UserSession();
		User user = new User("Alexey", 0);
		session.setUser(user);
		assertEquals(session.getUser(), user);
	}

	@Test
	public void testColorSetting() {
		UserSession session = new UserSession();
		session.setColor("blue");
		assertEquals(session.getColor(), "blue");
	}
	
	@Test
	public void testMyTurnSetting() {
		UserSession session = new UserSession();
		
		session.setMyTurn(true);
		assertTrue(session.isMyTurn());
		
		session.setMyTurn(false);
		assertFalse(session.isMyTurn());
	}
	
	@Test
	public void testFieldSetting() {
		UserSession session = new UserSession();
		Field field = new Field(20, 20);
		field.setPointState(0,  0, PointState.BLUE);
		session.setField(field);
		assertEquals(session.getField(), field);
	}
}
