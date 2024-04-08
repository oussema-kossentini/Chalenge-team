package tn.esprit.spring.services;

import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;

import java.util.Optional;

;
@Service

public class EmailService {

    @Autowired
    @Qualifier("mailSender")
    private  JavaMailSender mailSender;
    @Autowired

    @Qualifier("gmailSender")
    private  JavaMailSender gmailSender;
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    private final freemarker.template.Configuration freemarkerConfig;
    private final UserRepository userRepository; // Injection de dépendance pour UserRepository

    @Autowired
    public EmailService(@Qualifier("mailSender") JavaMailSender mailSender, @Qualifier("gmailSender") JavaMailSender gmailSender, FreeMarkerConfigurer freeMarkerConfigurer, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.gmailSender = gmailSender;
        this.freemarkerConfig = freeMarkerConfigurer.getConfiguration();
        this.userRepository = userRepository;
    }

    public void sendResetPasswordEmail(String subject, String email, String code) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            System.out.println("Email is in our data base " );
            User user = userOptional.get();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();

            ModelMap model = new ModelMap();
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("code", code);

            Template t= freemarkerConfig.getTemplate("SendResetPasswordEmailHtml.html");
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            System.out.println("Email is going to be sent  " );
            sendHtmlMessage(email, subject, htmlBody);
        }
    }



    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = gmailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("oussema.kossentini@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        gmailSender.send(message);
    }

    // Méthode pour envoyer un email HTML
   /* public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        // Configuration de la session de messagerie
        Session session = Session.getDefaultInstance(System.getProperties());

        // Création du message MIME
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("oussema.kossentini@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setContent(htmlBody, "text/html");

        // Envoi du message
        Transport.send(message);
    }*/
   /* public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("oussema.kossentini@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }*/


}
