package tn.esprit.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Value;
import java.util.Properties;

@Configuration
public class EmailConfiguration {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${gmail.mail.host}")
    private String gmailHost;

    @Value("${gmail.mail.port}")
    private int gmailPort;

    @Value("${gmail.mail.username}")
    private String gmailUsername;

    @Value("${gmail.mail.password}")
    private String gmailPassword;

    @Bean(name = "mailSender")
    public JavaMailSender mailSender() {
        return configureMailSender(mailHost, mailPort, mailUsername, mailPassword);
    }
    @Bean(name = "gmailSender")
    public JavaMailSender gmailSender() {
        return configureMailSender(gmailHost, gmailPort, gmailUsername, gmailPassword);
    }

    private JavaMailSender configureMailSender(String host, int port, String username, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.debug", "true");

        return mailSender;
    }
}
