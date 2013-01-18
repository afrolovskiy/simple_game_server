package ru.afrolovskiy.resourceSystem;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import ru.afrolovskiy.base.Resource;
import ru.afrolovskiy.base.ResourceFactory;
import ru.afrolovskiy.utils.SAXHandler;

public class ResourceFactoryImpl implements ResourceFactory {
	private static ResourceFactoryImpl resourceFactory = null;
	private Map<String, Resource> filePathToResource = new HashMap<String, Resource>();
	
	
	public Resource getResource(String filePath) {
		return (Resource) filePathToResource.get(filePath);
	}
	
	public void addResource(String filePath, String resourceClass) {
		// TODO: errors handling!! (file not found and others)
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler handler = new SAXHandler();
			saxParser.parse(filePath, handler);
			filePathToResource.put(filePath, (Resource) handler.getObject());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}
		
	private ResourceFactoryImpl() {}
	
	public static ResourceFactory getInstance() {
		if (resourceFactory == null) {
			resourceFactory = new ResourceFactoryImpl();
		}
		return resourceFactory;
	}

}
