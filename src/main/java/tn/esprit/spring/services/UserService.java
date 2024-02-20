package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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
        return userRepository.save(user); // Sauvegarde l'utilisateur avec l'image dans MongoDB
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

    @Override
    public User modifyUser(User updatedUser) {

        return userRepository.save(updatedUser);


    }


}
