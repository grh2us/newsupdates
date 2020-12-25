package com.realtime.newsupdates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsupdatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsupdatesApplication.class, args);
	}

}
