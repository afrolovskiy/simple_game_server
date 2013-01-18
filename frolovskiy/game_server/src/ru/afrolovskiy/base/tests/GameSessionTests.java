package ru.afrolovskiy.base.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.afrolovskiy.base.Field;
import ru.afrolovskiy.base.GameSession;
import ru.afrolovskiy.base.GameState;
import ru.afrolovskiy.base.PointState;

public class GameSessionTests {
	@Test
	public void testGameSteps() {
		int userId1 = 0;
		int userId2 = 1;
		GameSession session = new GameSession(userId1, userId2);
		
		assertEquals(session.getGameState(), GameState.Initialization);
		assertEquals(session.getUserId1(), userId1);
		assertEquals(session.getUserId2(), userId2);
		
		session.start();		
		assertEquals(session.getGameState(), GameState.Play);
		assertEquals(session.getCurrentStepUserId(), userId1);
		
		session.nextStep();
		assertEquals(session.getCurrentStepUserId(), userId2);
		
		session.nextStep();
		assertEquals(session.getCurrentStepUserId(), userId1);
		
		session.finish();
		assertEquals(session.getGameState(), GameState.Finish);
		assertEquals(session.getCurrentStepUserId(), userId1);
	}

	@Test
	public void testGetUserPointState() {
		int userId1 = 0;
		int userId2 = 1;
		GameSession session = new GameSession(userId1, userId2);
		
		assertEquals(session.getUserPointState(userId1), PointState.BLUE);
		assertEquals(session.getUserPointState(userId2), PointState.RED);
	}
	
	@Test
	public void testGetUserId() {
		int userId1 = 0;
		int userId2 = 1;
		GameSession session = new GameSession(userId1, userId2);
		
		assertEquals(session.getUserId1(), userId1);
		assertEquals(session.getUserId2(), userId2);
	}
	
	@Test
	public void testFieldChanging() {
		int userId1 = 0;
		int userId2 = 1;
		GameSession session = new GameSession(userId1, userId2);
		Field field = new Field(50, 50);
		session.start();
		
		session.addDot(userId1, 0, 0);
		field.setPointState(0, 0, PointState.BLUE);
		assertTrue(field.equals(session.getField()));
		session.nextStep();
		
		session.addDot(userId2, 0, 1);
		session.nextStep();
		session.addDot(userId2, 0, 2);
		field.setPointState(0, 1, PointState.RED);
		assertTrue(field.equals(session.getField()));
		
		session.addDot(userId1, 0, 0);
		assertTrue(field.equals(session.getField()));
				
		session.addDot(userId1, 0, 2);
		field.setPointState(0, 2, PointState.BLUE);
		assertTrue(field.equals(session.getField()));
	}
}
