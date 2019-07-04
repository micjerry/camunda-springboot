package org.camunda.bpm.getstarted.chargecard;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ChargeCardStarter {
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		ChargeCardWorker worker = new ChargeCardWorker();
		ChargeCardExecutorService.getExecutor().submit(worker);
	}
}
