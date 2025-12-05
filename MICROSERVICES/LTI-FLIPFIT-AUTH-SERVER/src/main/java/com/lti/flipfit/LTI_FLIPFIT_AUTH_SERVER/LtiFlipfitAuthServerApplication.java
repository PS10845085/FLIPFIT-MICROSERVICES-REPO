package com.lti.flipfit.LTI_FLIPFIT_AUTH_SERVER;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
@EnableDiscoveryClient


public class LtiFlipfitAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiFlipfitAuthServerApplication.class, args);
	}

}
