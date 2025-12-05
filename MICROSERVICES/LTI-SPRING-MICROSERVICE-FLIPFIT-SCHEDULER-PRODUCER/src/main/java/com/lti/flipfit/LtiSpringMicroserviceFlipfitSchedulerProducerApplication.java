package com.lti.flipfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
//@EnableDiscoveryClient
@EnableAutoConfiguration
@ComponentScan("com.lti.flipfit.*")
@EntityScan(basePackages = "com.lti.flipfit.entity")
@EnableJpaRepositories(basePackages = "com.lti.flipfit.repository")
public class LtiSpringMicroserviceFlipfitSchedulerProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiSpringMicroserviceFlipfitSchedulerProducerApplication.class, args);
	}

}
