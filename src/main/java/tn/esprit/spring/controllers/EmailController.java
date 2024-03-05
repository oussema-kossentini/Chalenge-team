package tn.esprit.spring.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {

    private final JavaMailSender javaMailSender;

    public EmailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/send-email")
    public String sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("mohamed.jasser.mriri@gmail.com");
        message.setSubject("New Course Added");
        message.setText("A new course has been added.");

        javaMailSender.send(message);

        return "Email sent successfully";
    }
}
