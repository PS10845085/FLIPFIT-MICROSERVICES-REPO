package com.flipfit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan("com.flipfit.*")
public class LtiDemoRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiDemoRestApplication.class, args);
	}

}
