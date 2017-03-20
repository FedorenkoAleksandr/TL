package com.fedorenko.onseo.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  <code>RequestPerSecondReport</code> print result of loading test.
 *
 * @author Fedorenko Oleksandr
 */
public class RequestPerSecondReport implements IReport {
	
	private static final Log logger = LogFactory.getLog(RequestPerSecondReport.class);
	
	private List<String> testResult;
	/**
    * Prints a result of report to console
    */
	public void printReport() {
		if(testResult != null && !testResult.isEmpty()) {
			for (String string : testResult) {
				System.out.println(string);
			}	
		} else {
			logger.error("It is complicated to print empty data");
		}
    }

	@Override
	public void setTestResult(List<String> testResult) {
		this.testResult = testResult;
	}
}
