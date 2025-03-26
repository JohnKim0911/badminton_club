package com.club.badminton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BadmintonApplication {

	public static void main(String[] args) {
		SpringApplication.run(BadmintonApplication.class, args);
	}

}
