package com.example.cs209project;

import com.example.cs209project.service.DataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(DataService service){
		return args -> {
			service.addEntries();
		};
	}

}
