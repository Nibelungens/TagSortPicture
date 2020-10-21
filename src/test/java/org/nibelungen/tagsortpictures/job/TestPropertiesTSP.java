package org.nibelungen.tagsortpictures.job;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPropertiesTSP {
	public static final String PROPERTIES_TEST = "TestProperties/test.properties";
	public static final String OK = "ok";
	
	@Test
	public void test_loadProperties() {
		PropertiesTSP prop = new PropertiesTSP();
		
		prop.addProperties(PROPERTIES_TEST);
		
		assertEquals(OK, prop.getProperty("test.test"));
	}
}
