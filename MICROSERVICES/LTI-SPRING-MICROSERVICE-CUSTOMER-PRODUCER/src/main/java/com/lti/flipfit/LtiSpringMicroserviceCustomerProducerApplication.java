package com.lti.flipfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LtiSpringMicroserviceCustomerProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiSpringMicroserviceCustomerProducerApplication.class, args);
	}

}
