package com.biblioTech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EpicChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpicChallengeApplication.class, args);
	}

}
