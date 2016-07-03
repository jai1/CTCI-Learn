package org.ctci.applications;

import java.util.HashMap;

public class Call {
	int callNumber = 0;
	HashMap<String, String> properties = new HashMap<String, String>();
	
	String setProperty(String name, String value) {
		return properties.put(name, value);
	}
	
	String getProperty(String name) {
		return properties.get(name);
	}
}
