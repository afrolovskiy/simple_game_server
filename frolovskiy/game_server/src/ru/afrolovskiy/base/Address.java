package ru.afrolovskiy.base;

import java.util.concurrent.atomic.AtomicInteger;

public class Address {
	private static AtomicInteger abonentCreator = new AtomicInteger();
	public final int abonentId;
	
	public Address() {
		this.abonentId = abonentCreator.getAndIncrement();
	}
	
	public int getAbonentId() {
		return this.abonentId;
	}
}
