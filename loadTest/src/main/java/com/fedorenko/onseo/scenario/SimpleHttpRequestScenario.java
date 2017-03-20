package com.fedorenko.onseo.scenario;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The {@code SimpleHttpRequestScenario} class represents simple scenario
 * for loading test. It sends request to HTTP service and collects data: 
 * date, time of execution, response code e.t.c
 * <p> 
 * The {@code SimpleHttpRequestScenario} Implements {@link * java.util.concurrent.Callable} 
 * means that it will be used to run as separate thread. Method call() is analog of method 
 * run() of {@link * java.lang.Runnable} with one difference it can returns parameter.    
 * 
 * @author  Fedorenko Oleksandr
 */
public class SimpleHttpRequestScenario implements Callable<String> {

	private static final Log logger = LogFactory.getLog(SimpleHttpRequestScenario.class);

	private volatile String httpServiceUrl;
	
	@Override
	public String call() throws Exception {
		int responseStatus = 0;
		String error = "";
		Long startTime = System.nanoTime();
	    if (StringUtils.isNotEmpty(httpServiceUrl)) {
			try {
				URL url = new URL(httpServiceUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				responseStatus = connection.getResponseCode();
			} catch (MalformedURLException e) {
				error = e.getMessage();
			} catch (IOException e) {
				error = e.getMessage();
			}
		}
		if (StringUtils.isNotEmpty(error)) {
			return error;
		}
		Long requestTime = System.nanoTime() - startTime;
		long durationOfRequest = TimeUnit.NANOSECONDS.toMillis(requestTime);
		return new Date() + ", " + "name:" + Thread.currentThread().getName()
				+ ", Address" + httpServiceUrl + ", duration:"
				+ durationOfRequest + " msec, Status: " + responseStatus;
	}
	
	public String getHttpServiceUrl() {
		return httpServiceUrl;
	}

	public void setHttpServiceUrl(String httpServiceUrl) {
		this.httpServiceUrl = httpServiceUrl;
	}
}