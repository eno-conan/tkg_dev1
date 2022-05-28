package com.eno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AtttemptApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtttemptApplication.class, args);
	}

}
