package ru.afrolovskiy.utils;

import java.lang.reflect.Field;

public class ReflectionHelper {
	public static Object createInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch(Exception err) {			
			// TODO: handle possible exceptions!
			System.out.println(err.getMessage());
		}
		return null;
	}
	
	public static void setFieldValue(Object object, String fieldName, String value) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			
			if (field.getType().equals(String.class)) {
				field.set(object, value);
			} else if (field.getType().equals(int.class)) {
				field.set(object, Integer.decode(value));
			}
			// TODO: other types handle
			
			field.setAccessible(false);
		} catch(Exception err) {
			System.out.println(err.getMessage());
			// TODO: handle possible exceptions!
		}
	}
}
