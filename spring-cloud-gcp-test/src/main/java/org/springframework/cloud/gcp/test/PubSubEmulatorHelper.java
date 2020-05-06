/*
 * Copyright 2017-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.gcp.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PubSubEmulatorHelper extends AbstractEmulatorHelper {
	private static final Log LOGGER = LogFactory.getLog(PubSubEmulatorHelper.class);

	String getGatingPropertyName() {
		return "it.pubsub-emulator";
	}

	String getEmulatorName() {
		return "pubsub";
	}

	@Override
	protected void afterEmulatorDestroyed() {
		String hostPort = getEmulatorHostPort();

		// find destory emulator process spawned by gcloud
		if (hostPort == null) {
			LOGGER.warn("Host/port null after the test.");
		}
		else {
			int portSeparatorIndex = hostPort.lastIndexOf(":");
			if (portSeparatorIndex < 0) {
				LOGGER.warn("Malformed host: " + hostPort);
				return;
			}

			String emulatorHost = hostPort.substring(0, portSeparatorIndex);
			String emulatorPort = hostPort.substring(portSeparatorIndex + 1);

			String hostPortParams = String.format("--host=%s --port=%s", emulatorHost, emulatorPort);
			killByCommand(hostPortParams);
		}
	}
}