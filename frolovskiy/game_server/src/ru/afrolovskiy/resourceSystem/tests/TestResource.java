package ru.afrolovskiy.resourceSystem.tests;

import ru.afrolovskiy.base.Resource;

public class TestResource implements Resource {
	private static final long serialVersionUID = 1878847734872969590L;
	private int a;
	private int b;
	
	public int getA() {
		return a;
	}
	
	public int getB() {
		return b;
	}
}