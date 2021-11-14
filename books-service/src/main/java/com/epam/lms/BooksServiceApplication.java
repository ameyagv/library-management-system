package com.epam.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BooksServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksServiceApplication.class, args);
	}

}
