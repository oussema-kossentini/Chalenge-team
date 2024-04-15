package tn.esprit.spring.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Properties;

@Configuration
@CrossOrigin(origins = "http://localhost:4200")
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // Set the SMTP hostname (assuming Gmail)
        mailSender.setPort(587); // Set the SMTP port

        mailSender.setUsername("alamdarm310@gmail.com"); // Set your Gmail username
        mailSender.setPassword("ehbs aind ltsv azos"); // Set your Gmail password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true"); // Enable SMTP authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        return mailSender;
    }
}
