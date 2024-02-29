package tn.esprit.spring.auth;


import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.spring.services.EmailService;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public boolean requestPasswordReset(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Random random = new Random();
            int code = 100000 + random.nextInt(900000);
            String resetToken = "courzello-" + code;
            user.setResetToken(resetToken);
            user.setResetTokenExpiration(LocalDateTime.now().plusMinutes(10));
            userRepository.save(user);
            String emailToBeSent = user.getEmail();
            //LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);
            //user.setResetTokenExpiration(expirationTime);

            // Envoi de l'e-mail avec le lien de réinitialisation du mot de passe
            try {
                emailService.sendResetPasswordEmail("Password Reset Request", emailToBeSent, resetToken);
                return true;
            } catch (Exception e) {
                // Gérer les erreurs d'envoi d'e-mail
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
/*
    public boolean verifyResetCode(String email,String code) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getResetToken().equals(code) ;
        }
        return false;
    }*/
public boolean verifyResetCode(String email, String code) {
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isPresent()) {
        User user = userOptional.get();
        // Check both token match and token not expired
        boolean tokenMatches = user.getResetToken().equals(code);
        boolean tokenNotExpired = LocalDateTime.now().isBefore(user.getResetTokenExpiration());

        return tokenMatches && tokenNotExpired;
    }
    return false;
}


    public boolean updatePassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword)); // Encode le nouveau mot de passe
            userRepository.save(user); // Sauvegarde les modifications dans la base de données
            return true;
        }
        return false;
    }

    /*public AuthenticationResponse register(RegisterRequest request) {

//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
//            try {
//                throw new AccessDeniedException("Only admins can register new users.");
//            } catch (AccessDeniedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

            userRepository.save(user);

            var jwtToken = jwtService.generateToken(new HashMap<>(),userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }



    // Dans votre méthode qui a besoin de UserDetails
    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        // Authentifier l'utilisateur via le AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Utiliser JwtUserDetailsService pour charger UserDetails
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getEmail());

        // Ici, vous pouvez utiliser userDetails pour vos besoins, par exemple, générer un JWT
        var jwtToken = jwtService.generateToken(new HashMap<>(), userDetails);

        // Retourner la réponse
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
*/


    //jawha bahi maghir taswira
  /*  public AuthenticationResponse register(RegisterRequest request) {
        // Conversion des informations de la demande en une nouvelle entité User
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Encodage du mot de passe
                .dateOfBirth(request.getDateOfBirth())
                .nationality(request.getNationality())
                .phone(request.getPhone())
                .statue(request.getStatue())
                .profilePicture((request.getProfilePicture()))
                .role(request.getRole()) // Ici, vous pouvez définir le rôle par défaut si nécessaire
                // Ajoutez d'autres champs ici
                .build();

        userRepository.save(user); // Sauvegarde de l'utilisateur dans la base de données

        // Chargement de UserDetails pour l'utilisateur nouvellement enregistré
        var jwtToken = jwtService.generateToken(new HashMap<>(),user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }*/

    public AuthenticationResponse register(RegisterRequest request, MultipartFile image) throws IOException {
        // Préparation de l'image
        byte[] imageBytes = null;
        if (image != null && !image.isEmpty()) {
            try {
                imageBytes = image.getBytes();
            } catch (IOException e) {
                // Il est généralement préférable de logger l'erreur et de lancer une exception spécifique à votre application
                throw new IOException("Échec de l'enregistrement de l'image téléchargée", e);
            }
        }

        // Création de l'entité User
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .nationality(request.getNationality())
                .phone(request.getPhone())
              //  .statue(request.getStatue())
                .profilePicture(imageBytes) // Utiliser imageBytes qui peut être null si aucune image n'est fournie
                .role(request.getRole())
                .build();

        // Sauvegarde de l'utilisateur
        userRepository.save(user);

        // Génération du token JWT
        String jwtToken = jwtService.generateToken(new HashMap<>(), user);

        // Construction de la réponses
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }



  /*  public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user =userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(new HashMap<>(),user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }*/


        public AuthenticationResponse authenticate (AuthenticateRequest request){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Vérifiez et imprimez les autorités de l'utilisateur
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("User has authorities: " + userDetails.getAuthorities());

            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(new HashMap<>(), user);
            return AuthenticationResponse.builder().token(jwtToken).build();
        }


    }

