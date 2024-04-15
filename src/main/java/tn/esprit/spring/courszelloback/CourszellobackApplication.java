package tn.esprit.spring.courszelloback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EntityScan("tn.esprit.entities")
@ComponentScan( "tn.esprit.spring")
@EnableMongoRepositories(basePackages = "tn.esprit.spring.repositories")
@SpringBootApplication
@EnableWebMvc
public class CourszellobackApplication  implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CourszellobackApplication.class, args);
	}

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**") // Spécifiez le chemin de votre API
				.allowedOrigins("http://localhost:4200") // Autorisez les requêtes depuis ce domaine
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Autorisez les méthodes HTTP spécifiées
				.allowCredentials(true)// Autorisez l'envoi des cookies
				.allowedHeaders("*");

	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new MultipartFileToByteArrayConverter());
	}




}