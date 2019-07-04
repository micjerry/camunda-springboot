package org.camunda.bpm.getstarted.chargecard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ChargeCardExecutorService {
	private static ScheduledExecutorService executor = null;
	
	public synchronized static ScheduledExecutorService getExecutor() {
		if (executor == null) {
			executor = Executors.newScheduledThreadPool(5);
		}
		
		return executor;
	}
}
