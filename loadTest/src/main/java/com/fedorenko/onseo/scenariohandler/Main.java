package com.fedorenko.onseo.scenariohandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fedorenko.onseo.report.IReport;
import com.fedorenko.onseo.util.propertiesreader.PropertyUtils;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	private static final Log logger = LogFactory.getLog(Main.class);
	
	private static final String PROPERTY_UTIL = "propertyUtils";
	private static final String SCENARIO_HANDLER = "scenarioHandler";
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		logger.info("STRESS TEST");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");
		PropertyUtils config = null;
		IScenarioHandler scenarioHandler = null;
		if(ctx.containsBean(PROPERTY_UTIL)){
			config = (PropertyUtils) ctx.getBean(PROPERTY_UTIL);
		}else{
			throw new NoSuchBeanDefinitionException(PROPERTY_UTIL);
		}
		String beanName = config.getProperty(SCENARIO_HANDLER);
		if(StringUtils.isNotEmpty(beanName) &&  ctx.containsBean(beanName)){
			scenarioHandler = (IScenarioHandler) ctx.getBean(config.getProperty(SCENARIO_HANDLER));
		}else{
			throw new NoSuchBeanDefinitionException(config.getProperty(SCENARIO_HANDLER));
		}
		ctx.close();
		IReport report = scenarioHandler.startTest();
		report.printReport();
		System.exit(0);
	}
}