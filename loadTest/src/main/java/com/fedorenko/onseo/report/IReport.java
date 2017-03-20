package com.fedorenko.onseo.report;

import java.util.List;

/**
 * IReport is used to represent a report of loading test execution.
 * 
 * @author Fedorenko Oleksandr
 */
public interface IReport {
	public void printReport();
	public void setTestResult(List<String> testData);
}
