package ru.afrolovskiy.base.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.afrolovskiy.base.Field;
import ru.afrolovskiy.base.PointState;

public class FieldTests {
	@Test
	public void testFieldInitialization() {
		int rowCount = 10;
		int columnCount = 10;
		Field field = new Field(rowCount, columnCount);
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				assertEquals(field.getPointState(i, j), PointState.EMPTY);
			}
		}
	}
	
	@Test
	public void testFieldSizeGetting() {
		int rowCount = 10;
		int columnCount = 10;
		Field field = new Field(rowCount, columnCount);
		assertEquals(field.getRowCount(), rowCount);
		assertEquals(field.getColumnCount(), columnCount);
	}
	
	@Test
	public void testPointStatesChanging() {
		int rowCount = 10;
		int columnCount = 10;
		Field field = new Field(rowCount, columnCount);
		
		field.setPointState(0, 0, PointState.BLUE);
		assertEquals(field.getPointState(0, 0), PointState.BLUE);
		
		field.setPointState(9, 9, PointState.RED);
		assertEquals(field.getPointState(9, 9), PointState.RED);
				
		Throwable err = null;
		try {
			field.setPointState(10, 10, PointState.BLUE);
		} catch (Throwable e) {
			err = e;
		}
		assertTrue(err instanceof IndexOutOfBoundsException);		

		err = null;
		try {
			field.getPointState(10, 10);
		} catch (Throwable e) {
			err = e;
		}
		assertTrue(err instanceof IndexOutOfBoundsException);
		
		err = null;
		try {
			field.setPointState(-1, -1, PointState.BLUE);
		} catch (Throwable e) {
			err = e;
		}
		assertTrue(err instanceof IndexOutOfBoundsException);		
		
		err = null;
		try {
			field.getPointState(-1, -1);
		} catch (Throwable e) {
			err = e;
		}
		assertTrue(err instanceof IndexOutOfBoundsException);
	}
	
	@Test
	public void testIsEmpty() {
		int rowCount = 10;
		int columnCount = 10;
		Field field = new Field(rowCount, columnCount);
		
		assertTrue(field.isEmpty(0, 0));
		
		field.setPointState(1, 1, PointState.BLUE);
		assertFalse(field.isEmpty(1, 1));
	
		Throwable err = null;
		try {
			field.isEmpty(-1, -1);
		} catch (Throwable e) {
			err = e;
		}
		assertTrue(err instanceof IndexOutOfBoundsException);
		
		err = null;
		try {
			field.isEmpty(10, 10);
		} catch (Throwable e) {
			err = e;
		}
		assertTrue(err instanceof IndexOutOfBoundsException);
	}
	
	@Test
	public void testToJSON() {
		int rowCount = 10;
		int columnCount = 10;
		Field field = new Field(rowCount, columnCount);
		
		assertEquals(field.toJSON(), "'[]'");
		
		field.setPointState(0, 0, PointState.BLUE);
		assertEquals(field.toJSON(), "'[[0,0,\"blue\"]]'");
		
		field.setPointState(1, 1, PointState.RED);
		field.setPointState(1, 2, PointState.RED);
		assertEquals(field.toJSON(), "'[[0,0,\"blue\"],[1,1,\"red\"],[1,2,\"red\"]]'");
	}
	
	@Test
	public void testEquals() {
		int rowCount = 10;
		int columnCount = 10;
		Field field1 = new Field(rowCount, columnCount);
		Field field2 = new Field(rowCount, columnCount);
		
		assertTrue(field1.equals(field2));
		
		field1.setPointState(0, 0, PointState.BLUE);
		assertFalse(field1.equals(field2));
		
		field2.setPointState(0, 0, PointState.BLUE);
		assertTrue(field1.equals(field2));
		
		field1 = new Field(10, 10);
		field2 = new Field(20, 20);
		assertFalse(field1.equals(field2));
	}
}

