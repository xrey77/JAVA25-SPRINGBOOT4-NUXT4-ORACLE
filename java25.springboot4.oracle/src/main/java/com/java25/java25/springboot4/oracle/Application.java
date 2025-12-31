package com.java25.java25.springboot4.oracle;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.java25.java25.springboot4.oracle.entities")
@EnableJpaRepositories("com.java25.java25.springboot4.oracle.repositories")
public class Application {
	
	@Bean
	public ModelMapper modelMapper() {
	        return new ModelMapper();
	}	
	
	@Bean
	public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	}	
		
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}	
}