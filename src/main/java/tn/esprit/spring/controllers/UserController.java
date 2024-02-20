package tn.esprit.spring.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Nationality;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.IUserService;
import java.util.Optional;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins ="http://localhost:4200\"")
public class UserController {

    final IUserService userService;
    final ObjectMapper objectMapper; // Jackson's ObjectMapper
    UserRepository userRepository;

 /*   @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestParam("user") String userJson,
                                        @RequestParam(value = "profilePicture", required = false) MultipartFile file) throws IOException {
        User user = objectMapper.readValue(userJson, User.class); // Convertit userJson en objet User
        if (file != null && !file.isEmpty()) {
            user.setProfilePicture(file.getBytes());
        }
        User savedUser = userService.addUser(user);
        return ResponseEntity.ok(savedUser);
    }*/






        @GetMapping("/nationalities")
        public Nationality[] getNationalities() {
            return Nationality.values();
        }

        @GetMapping("/roles")
        public Role[] getRoles() {
            return Role.values();
        }



    @JsonCreator
    public static Nationality fromString(String value) {
        for (Nationality nationality : Nationality.values()) {
            if (nationality.name().equalsIgnoreCase(value)) {
                return nationality;
            }
        }
        throw new IllegalArgumentException("Nationality with value " + value + " not found");
    }

/*
    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public ResponseEntity<User> addUser(
            @RequestPart("user") String userJson,
            @RequestPart(value = "profilePicture", required = false) MultipartFile file) throws IOException {
        try {
        User user = objectMapper.readValue(userJson, User.class);
        User savedUser = userService.addUserimage(user, file);


        return ResponseEntity.ok(savedUser);
    } catch (IOException ex) {
            throw new RuntimeException("Error processing request", ex);
        }

    }

*/
@PostMapping(value = "/add", consumes = {"multipart/form-data"})
public ResponseEntity<?> addUser(
        @ModelAttribute @Valid User user,
        @RequestPart(value = "profilePicture", required = false) MultipartFile file,
        @RequestParam("dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth
) {
    try {
        if (file != null && !file.isEmpty()) {
            byte[] profilePictureBytes = file.getBytes();
            user.setProfilePicture(profilePictureBytes);
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Erreur: Cet email est déjà utilisé!");
        }


        User savedUser = userService.addUserimage(user,file );
        return ResponseEntity.ok(savedUser);
    } catch (IOException e) {
        // Capturez l'erreur et renvoyez un message d'erreur approprié
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors du traitement des données utilisateur : " + e.getMessage());
    } catch (Exception ex) {
        // Capturez toute autre exception et renvoyez un message d'erreur approprié
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du traitement de la requête : " + ex.getMessage());
    }
}








    @GetMapping("/retrieve-all-users")
    public List<User> getUsers() {
        return userService.retrieveAllUsers();
    }

    @DeleteMapping("/remove-user/{user-id}")
    public ResponseEntity<Void> removeUser(@PathVariable("user-id") String userId) {
        userService.removeUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/modify-user")
    public ResponseEntity<User> modifyUser(@RequestParam("user") String userJson,
                                           @RequestParam(value = "profilePicture", required = false) MultipartFile file) throws IOException {
        User user = objectMapper.readValue(userJson, User.class); // Convertit userJson en objet User
        if (file != null && !file.isEmpty()) {
          //  user.setProfilePicture(file.getBytes());
        }
        User updatedUser = userService.addUser(user); // Cette méthode pourrait nécessiter une logique distincte pour "mettre à jour" vs "ajouter"
        return ResponseEntity.ok(updatedUser);
    }
}
