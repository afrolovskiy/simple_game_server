package ru.afrolovskiy.resourceSystem.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import ru.afrolovskiy.base.ResourceFactory;
import ru.afrolovskiy.resourceSystem.ResourceFactoryImpl;

public class ResourceFactoryTests {

	@Test
	public void test() {
		ResourceFactory resourceFactory = ResourceFactoryImpl.getInstance();
		resourceFactory.addResource("src/ru/afrolovskiy/resourceSystem/tests/TestResource.xml", 
				TestResource.class.getName());
		TestResource testResource = (TestResource) resourceFactory.getResource("src/ru/afrolovskiy/resourceSystem/tests/TestResource.xml");
		assertEquals(testResource.getA(), 123);
		assertEquals(testResource.getB(), 1324);		
	}

}
