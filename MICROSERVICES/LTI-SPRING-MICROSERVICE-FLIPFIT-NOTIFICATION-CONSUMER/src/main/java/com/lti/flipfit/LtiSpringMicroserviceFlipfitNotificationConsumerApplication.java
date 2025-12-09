package com.lti.flipfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class LtiSpringMicroserviceFlipfitNotificationConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiSpringMicroserviceFlipfitNotificationConsumerApplication.class, args);
	}

}
