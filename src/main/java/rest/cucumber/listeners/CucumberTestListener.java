package rest.cucumber.listeners;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CucumberTestListener implements ConcurrentEventListener {
	private static final Logger LOG = LogManager.getLogger(CucumberTestListener.class);
	
	@Override
	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
	}

	private void handleTestCaseFinished(TestCaseFinished event) {
		TestCase testCase = event.getTestCase();
		Result result = event.getResult();
		Status status = result.getStatus();
		Throwable error = result.getError();
		String scenarioName = testCase.getName();		
		if(error != null) {
			LOG.info(error);
		}
		LOG.info("*****************************************************************************************");
		LOG.info("	Scenario: "+scenarioName+" --> "+status.name());
		LOG.info("*****************************************************************************************");
	}
}