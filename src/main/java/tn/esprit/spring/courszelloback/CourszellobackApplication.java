package tn.esprit.spring.courszelloback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EntityScan("tn.esprit.entities")
@ComponentScan("tn.esprit.spring")
@EnableMongoRepositories(basePackages = "tn.esprit.spring.repositories")
@SpringBootApplication
@EnableScheduling
public class CourszellobackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourszellobackApplication.class, args);
	}

	// Configuration CORS
	
}