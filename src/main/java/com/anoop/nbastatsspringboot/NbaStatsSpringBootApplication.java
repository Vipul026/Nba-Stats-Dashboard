package com.anoop.nbastatsspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableAutoConfiguration
public class NbaStatsSpringBootApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(NbaStatsSpringBootApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NbaStatsSpringBootApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Player player = restTemplate.getForObject(
					"https://www.balldontlie.io/api/v1/players/1", Player.class);
			LOGGER.info(player.toString());
		};
	}

}
