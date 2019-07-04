/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.getstarted.chargecard;

import org.camunda.bpm.client.ExternalTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChargeCardWorker implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(ChargeCardWorker.class);
	
	@Override
	public void run() {
		LOGGER.info("create worker");
		ExternalTaskClient client = ExternalTaskClient.create()
				.baseUrl("http://127.0.0.1:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();
		
		client.subscribe("charge-card")
		.lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
		.handler((externalTask, externalTaskService) -> {
			String item = (String) externalTask.getVariable("item");
			Long amount = (Long) externalTask.getVariable("amount");
			LOGGER.info("Charging credit card with an amount of '" + amount + "for the item '" + item + "'...");

			// Complete the task
			externalTaskService.complete(externalTask);
		})
		.open();		
	}
}
