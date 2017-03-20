package com.fedorenko.onseo.scenariohandler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fedorenko.onseo.report.IReport;

public class SimpleSendHttpRequestHandler implements IScenarioHandler {
	
	private static final Log logger = LogFactory.getLog(SimpleSendHttpRequestHandler.class);
	
	private int requestPerSecond;
	private final static long DURATION = 1;
	private Callable<String> scenario;
	private IReport report;
	
	public SimpleSendHttpRequestHandler(Callable<String> scenario){
		this.scenario = scenario;
	}
	public IReport startTest() {
		ExecutorService executor = Executors.newFixedThreadPool(requestPerSecond);
		List<Future<String>> testResult = new LinkedList<Future<String>>();
		int i = 0;
		logger.info("Start send request.");
		while(i < requestPerSecond) {
        	Future<String> threadData = (Future<String>) executor.submit(scenario);
        	testResult.add(threadData);
        	i++;
        }
		
		try {
			TimeUnit.SECONDS.sleep(DURATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		executor.shutdownNow();
		logger.info("End send request.");
		report.setTestResult(checkResult(testResult));
        return report;
	}
	
	private List<String> checkResult(List<Future<String>> testResult) {
		List<String> checkedResult = new LinkedList<String>();
		if(testResult !=null && !testResult.isEmpty()) {
			for(Future<String> result : testResult) {
				if(result.isDone()) {
				try {
					checkedResult.add(result.get());
				} catch (InterruptedException e) {
					logger.info("Thread was bloked");
					e.printStackTrace();
				} catch (ExecutionException e) {
					logger.info("Thread was bloked");
					e.printStackTrace();
				}
				} else {
					result.cancel(true);
					checkedResult.add("Test fail");
				}
			}
		}
		
		return checkedResult;
	}
	
	public int getRequestPerSecond() {
		return requestPerSecond;
	}

	public void setRequestPerSecond(int requestPerSecond) {
		this.requestPerSecond = requestPerSecond;
	}
	
	public Callable<String> getScenario() {
		return scenario;
	}
	
	public void setScenario(Callable<String> scenario) {
		this.scenario = scenario;
	}
	public IReport getReport() {
		return report;
	}
	public void setReport(IReport report) {
		this.report = report;
	}
}