package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.auth.AuthenticationResponse;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
@Service
@AllArgsConstructor
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;
    private  final  EmailService emailService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    private final Path rootLocation = Paths.get("path/to/your/uploaded/images");

    public User addUserimage(User user, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                byte[] imageBytes = image.getBytes(); // Tente de lire les bytes de l'image
                user.setProfilePicture(imageBytes); // Stocke les bytes dans le champ profilePicture de l'utilisateur
            } catch (IOException e) {
                throw new RuntimeException("Failed to store uploaded image", e);
            }


        }
        String encodedPassword = passwordEncoder.encode(user.getPassword()); // Hacher le mot de passe
        user.setPassword(encodedPassword); // Définir le mot de passe haché
        user.setStatue(true);
        //definir le token

        String jwtToken = jwtService.generateToken(new HashMap<>(), user);

        // Construction de la réponses

        return userRepository.save(user); // Sauvegarde l'utilisateur avec l'image dans MongoDB
    }




    public boolean requestPasswordReset(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
          /*  User user = userOptional.get();
            String resetToken = UUID.randomUUID().toString();
            user.setResetToken(resetToken);*/
            // Génération d'un code de 6 chiffres
            User user = userOptional.get();
            Random random = new Random();
            int code = 100000 + random.nextInt(900000);
            // Concaténation avec "courzello-"
            String resetToken = "courzello-" + code;
            user.setResetToken(resetToken);
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);
            user.setResetTokenExpiration(expirationTime);
            userRepository.save(user);
            String emailTobeSent =user.getEmail();

            // Envoi de l'e-mail avec le lien de réinitialisation du mot de passe
            try {
                emailService.sendResetPasswordEmail("Password Reset Request",emailTobeSent, resetToken);

                return true;
            } catch (Exception e) {
                // Gérer les erreurs d'envoi d'e-mail
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


    public boolean verifyResetCode(String email, String code) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getResetToken().equals(code);
        }
        return false;
    }

    // Autres méthodes...




    @Override
    public User addUser(User user) {


        return userRepository.save(user);
    }


    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();

    }

    @Override
    public void removeUser(String id) {
        userRepository.deleteById(id);

    }




    /*@Override
    public User modifyUser(User updatedUser) {

        return userRepository.save(updatedUser);


    }*/

    @Autowired
    private ObjectMapper objectMapper; // Assurez-vous d'avoir configuré un bean ObjectMapper

    public User modifyUser(String userId, User updatedUser, MultipartFile image) {
        // Trouver l'utilisateur existant par ID
        return userRepository.findById(userId).map(existingUser -> {
            try {
                // Mise à jour des champs de l'utilisateur existant avec les nouvelles valeurs
                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                // Ajouter ici d'autres champs à mettre à jour si nécessaire

                // Mise à jour de l'image de profil si une nouvelle image est fournie
                if (image != null && !image.isEmpty()) {
                    byte[] imageBytes = image.getBytes();
                    existingUser.setProfilePicture(imageBytes);
                }

                // Sauvegarde des modifications dans la base de données
                userRepository.save(existingUser);

                return existingUser;
            } catch (IOException e) {
                throw new RuntimeException("Error processing user information", e);
            }
        }).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }



}