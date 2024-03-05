package tn.esprit.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration

public class AppConfig {
    @Bean
    public String imageDirectory() {
        return "C:\\Users\\oumai\\OneDrive\\Bureau\\imagesvidpii";
    }

}
