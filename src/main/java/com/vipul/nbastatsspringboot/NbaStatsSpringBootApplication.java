package com.vipul.nbastatsspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NbaStatsSpringBootApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(NbaStatsSpringBootApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NbaStatsSpringBootApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


}
