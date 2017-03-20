package com.fedorenko.onseo.util.propertiesreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beanTest.xml" })
public class PropertyUtilsTest {
	
	private static final String CONFIG_FILE_PATH = "src/test/resources/testconfig/configTest.properties";
	private static final String PROPERTY_UTIL = "propertyUtils";
	private static final String SCENARIO_HANDLER = "scenarioHandler";
	private static final String SCENARIO = "scenario";
	private static final String REPORT = "report";
	private static final String REQUEST_PER_SECOND = "requestPerSeconds";
	private static final String URL = "url";
	private static final String FAKE = "fake";
	
	private ClassPathXmlApplicationContext ctx;
	private PropertyUtils propertyUtils;
	private Properties p;
	
	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("beantest.xml");
		propertyUtils = (PropertyUtils) ctx.getBean(PROPERTY_UTIL);
		p = new Properties();
	}
	
	@Test(expected=FileNotFoundException.class)
	public void getProperty() throws FileNotFoundException, IOException {
		p.load(new FileReader(new File(FAKE)));
		p.load(new FileReader(new File(CONFIG_FILE_PATH)));
		
		Assert.assertTrue(propertyUtils.getProperty(SCENARIO_HANDLER).equals(p.get(SCENARIO_HANDLER)));
		Assert.assertTrue(propertyUtils.getProperty(SCENARIO).equals(p.get(SCENARIO)));
		Assert.assertTrue(propertyUtils.getProperty(REPORT).equals(p.get(REPORT)));
		Assert.assertTrue(propertyUtils.getProperty(REQUEST_PER_SECOND).equals(p.get(REQUEST_PER_SECOND)));
		Assert.assertTrue(propertyUtils.getProperty(URL).equals(p.get(URL)));
		Assert.assertNull(propertyUtils.getProperty(FAKE));
	}
	
}
