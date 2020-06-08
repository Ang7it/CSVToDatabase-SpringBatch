package com.nova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class CsvToDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvToDatabaseApplication.class, args);
	}

}
