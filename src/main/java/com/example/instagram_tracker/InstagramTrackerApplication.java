package com.example.instagram_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class InstagramTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagramTrackerApplication.class, args);
	}

}
