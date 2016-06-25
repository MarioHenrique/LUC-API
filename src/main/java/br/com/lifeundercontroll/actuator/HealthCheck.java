package br.com.lifeundercontroll.actuator;

import org.apache.log4j.Logger;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck implements HealthIndicator{

	Logger LOGGER = Logger.getLogger(HealthCheck.class);
	
	@Override
	public Health health() {
		LOGGER.debug("System HealthCheck logging");
		return Health.up().build();
	}

}
