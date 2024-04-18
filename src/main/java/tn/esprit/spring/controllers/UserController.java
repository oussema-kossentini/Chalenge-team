package tn.esprit.spring.controllers;
import org.apache.coyote.BadRequestException;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.Nationality;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.IUserService;

import java.util.*;


import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins ="http://localhost:4200")
public class UserController {

    final IUserService userService;
    final ObjectMapper objectMapper; // Jackson's ObjectMapper
    final JwtService jwtService;
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
        @PostMapping("/forgot-password")
        public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
            String email = request.get("email");
            boolean result = userService.requestPasswordReset(email);
            if (result) {
                return ResponseEntity.ok().body("Reset password email sent.");
            } else {
                return ResponseEntity.badRequest().body("Email not found.");
            }
        }
    */
//@PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        System.out.println("Email received: " + email); // Ajouter cette ligne pour déboguer
        boolean result = userService.requestPasswordReset(email);
        if (result) {
            return ResponseEntity.ok().body("Reset password email sent.");
        }
        return ResponseEntity.badRequest().body("Email not found.");
    }
    // @PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
    @PostMapping("/verify-reset-code")
    public ResponseEntity<?> verifyResetCode(@RequestParam("email") String email,@RequestParam("code") String code) {
        boolean isValid = userService.verifyResetCode(email,code);
        if (isValid) {
            return ResponseEntity.ok().body("Code verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid code.");
        }
    }

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addUser(
            @ModelAttribute @Valid User user,
            @RequestPart(value = "profilePicture", required = false) MultipartFile file,
            @RequestParam(value = "dateOfBirth",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth
    ) {
        try {
            if (file != null && !file.isEmpty()) {
                byte[] profilePictureBytes = file.getBytes();
                user.setProfilePicture(profilePictureBytes);
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





/*
    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addUser(
            @ModelAttribute @Valid User user,
            @RequestPart(value = "profilePicture", required = false) MultipartFile file,
            @RequestParam("dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth
    ) {
        try {
            // Votre logique pour enregistrer l'utilisateur, incluant l'ajout de l'image de profil
            User savedUser = userService.addUserimage(user, file);
            // Obtenez les détails de l'utilisateur nécessaires à la génération du token JWT
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    savedUser.getEmail(), // Utilisez un champ approprié de votre objet User comme nom d'utilisateur
                    savedUser.getPassword(), // Utilisez un champ approprié de votre objet User comme mot de passe
                    // Ajoutez les rôles ou les autorités de l'utilisateur s'ils sont disponibles dans votre objet User
                    // Utilisez savedUser.getAuthorities() si vos utilisateurs ont des rôles définis
                    // Sinon, vous devrez définir les rôles ou les autorités manuellement
                    Collections.emptyList() // Pour l'instant, nous utilisons une liste vide d'autorités
            );

            // Générer le token JWT
            Map<String, Object> extraClaims = new HashMap<>();
            // Ajoutez des informations supplémentaires au JWT si nécessaire
            String token = jwtService.generateToken(extraClaims, userDetails);

            // Ajouter le token JWT à la réponse
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Utilisateur enregistré avec succès");

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // Capturez toute autre exception et renvoyez un message d'erreur approprié
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du traitement de la requête : " + ex.getMessage());
        }
    }

*/


    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @GetMapping("/retrieve-all-users")
    public List<User> getUsers(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return userService.retrieveAllUsers();
    }

    /*@DeleteMapping("/remove-user/{user-id}")
    public ResponseEntity<Void> removeUser(@PathVariable("user-id") String userId) {
        userService.removeUser(userId);
        return ResponseEntity.ok().build();
    }*/
    @DeleteMapping("/remove-user/{userId}")
    public void removeUser(@PathVariable("userId") String userId) {
        userService.removeUser(userId);
    }


    // @PreAuthorize("hasRole('ADMINSTRATOR')")
   /* @PutMapping("/modify-user")
    public ResponseEntity<User> modifyUser(@RequestParam("user") String userJson,
                                           @RequestParam(value = "profilePicture", required = false) MultipartFile file) throws IOException {
        User user = objectMapper.readValue(userJson, User.class); // Convertit userJson en objet User
        if (file != null && !file.isEmpty()) {
          //  user.setProfilePicture(file.getBytes());
        }
        User updatedUser = userService.addUser(user); // Cette méthode pourrait nécessiter une logique distincte pour "mettre à jour" vs "ajouter"
        return ResponseEntity.ok(updatedUser);
    }*/
    @PutMapping("/modify-user/{idUser}")
    public ResponseEntity<User> modifyUser(
            @PathVariable("idUser") String idUser,
            @RequestParam String userJson, // User details as JSON string
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            User updatedUserDetails = mapper.readValue(userJson, User.class); // Parse JSON to User object

            return userRepository.findById(idUser).map(existingUser -> {

                try {
                    // Update fields with new values
                    existingUser.setFirstName(updatedUserDetails.getFirstName());
                    existingUser.setLastName(updatedUserDetails.getLastName());
                    // ... Update other fields as needed

                    // Update profile picture if a new image is provided
                    if (image != null && !image.isEmpty()) {
                        byte[] imageBytes = image.getBytes();
                        existingUser.setProfilePicture(imageBytes);
                    }

                    // Save changes to the database
                    User savedUser = userRepository.save(existingUser);
                    return ResponseEntity.ok(savedUser); // Return updated user with 200 OK status

                } catch (IOException e) {
                    throw new RuntimeException("Error processing image", e);
                }

            }).orElseThrow(() -> new NoSuchElementException("User not found with ID " + idUser));

        } catch (JsonProcessingException e) {
            throw new Exception("Invalid user data format", e);
        }
    }





}