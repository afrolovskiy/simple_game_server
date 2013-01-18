package ru.afrolovskiy.base;

public interface ResourceFactory {
	void addResource(String filePath, String resourceClass);
	Resource getResource(String filePath);
}
