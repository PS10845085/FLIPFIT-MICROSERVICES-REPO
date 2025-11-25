package com.lti.flipfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LtiSpringRestFlipfitApiJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiSpringRestFlipfitApiJpaApplication.class, args);
	}

}
