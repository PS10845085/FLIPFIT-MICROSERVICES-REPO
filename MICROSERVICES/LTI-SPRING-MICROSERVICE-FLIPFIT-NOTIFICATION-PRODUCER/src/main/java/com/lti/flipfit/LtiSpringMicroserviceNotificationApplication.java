package com.lti.flipfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("com.flipfit.*")
public class LtiSpringMicroserviceNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiSpringMicroserviceNotificationApplication.class, args);
	}

}
