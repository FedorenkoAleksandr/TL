package com.fedorenko.onseo.scenariohandler;

import com.fedorenko.onseo.report.IReport;

/**
 * IScenarioHandler is interface for classes which 
 * will start and define scenario for load testing.
 * 
 * @author Fedorenko Oleksandr
 */
public interface IScenarioHandler {
	public IReport startTest();
}
