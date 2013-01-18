package ru.afrolovskiy.accountService;

import java.util.HashMap;
import java.util.Map;

import ru.afrolovskiy.base.Users;

public class MockUsers implements Users {
	private Map<String, Integer> userIds;
	
	public MockUsers() {
		this.userIds = new HashMap<String, Integer>();
		userIds.put("Alexey", 1);
		userIds.put("Elena", 2);
		userIds.put("Anna", 3);
		userIds.put("Alexander", 4);
	}
	
	public Integer getId(String name) {
		return userIds.get(name);
	}
}
