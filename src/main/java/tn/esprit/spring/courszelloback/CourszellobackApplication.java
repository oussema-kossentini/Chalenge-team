package tn.esprit.spring.courszelloback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EntityScan("tn.esprit.entities")
@ComponentScan( "tn.esprit.spring")
@EnableMongoRepositories(basePackages = "tn.esprit.spring.repositories")
@SpringBootApplication
public class CourszellobackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourszellobackApplication.class, args);
	}

}
