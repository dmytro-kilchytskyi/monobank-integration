package org.cloudstats.monobankintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MonobankIntegrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(MonobankIntegrationApplication.class, args);
	}
}
