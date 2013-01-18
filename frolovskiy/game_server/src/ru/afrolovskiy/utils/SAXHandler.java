package ru.afrolovskiy.utils;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {
	private static String CLASSNAME = "class";
	private String element = null;
	private Object object = null;
	private boolean inElement = false;

	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (!qName.equals(CLASSNAME)) {
			element = qName;
			inElement = true;
		} else {
			String className = attributes.getValue(0);
			System.out.println("Class name: " + className);
			object = ReflectionHelper.createInstance(className);
		}
	}
	
	public void endElement(String uri, String loccalame, String qName) {
		element = null;
		inElement = false;
	}
	
	public void characters(char ch[], int start, int length) {
		if (inElement && element != null) {
			String value = new String(ch, start, length);
			System.out.println(element + "=" + value);
			ReflectionHelper.setFieldValue(object,  element,  value);
		}
	}
	
	public Object getObject() {
		return object;
	}
}

