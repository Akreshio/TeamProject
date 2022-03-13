package ru.intervale.TeamProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TeamProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamProjectApplication.class, args);
	}

}
