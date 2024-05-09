package tn.esprit.spring.courszelloback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.CacheControl.maxAge;

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
              //   .maxAge(3600);
	}
	/*public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
				.allowedOrigins("http://localhost:4200")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowCredentials(true)
				.allowedHeaders("*")
				.exposedHeaders("Header1", "Header2") // Specify which headers can be exposed to the browser.
				.maxAge(3600); // How long the response from a pre-flight request can be cached by clients.
	}
*/
	@ControllerAdvice
	public class GlobalControllerAdvice {
		@RequestMapping(method = RequestMethod.OPTIONS)
		public ResponseEntity handleOptions() {
			return ResponseEntity.ok().build();
		}
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new MultipartFileToByteArrayConverter());
	}


	@ControllerAdvice
	public class GlobalExceptionHandler {


		@ExceptionHandler(UsernameNotFoundException.class)
		public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("timestamp", new Date());
			errorDetails.put("error", "User Not Found");
			errorDetails.put("message", ex.getMessage());
			errorDetails.put("path", request.getDescription(false).replace("uri=", ""));
			return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		}

		@ExceptionHandler(BadCredentialsException.class)
		public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("timestamp", new Date());
			errorDetails.put("error", "Invalid Credentials");
			errorDetails.put("message", "The credentials provided are incorrect.");
			errorDetails.put("path", request.getDescription(false).replace("uri=", ""));
			return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
		}
	}

}