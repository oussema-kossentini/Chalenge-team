package tn.esprit.spring.auth;


import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.entities.UserOAuth2User;
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
import java.util.*;

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

    //lazem rakaha
/*
    public AuthenticationResponse register(RegisterRequest request) throws IOException {
        // Préparation de l'image
        byte[] profilePictureBytes = request.getProfilePicture();

        // Création de l'entité User
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .nationality(request.getNationality())
                .phone(request.getPhone())
                .statue(Boolean.TRUE)
                .profilePicture(profilePictureBytes) // Utiliser imageBytes qui peut être null si aucune image n'est fournie
              // .role(Role.valueOf("USER"))

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
*/


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

    public AuthenticationResponse addUserimage(User user, MultipartFile image) {
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

        userRepository.save(user); // Sauvegarde l'utilisateur avec l'image dans MongoDB

        // Générer un token JWT pour l'utilisateur nouvellement créé
        var jwtToken = jwtService.generateToken(new HashMap<>(), user);

        // Retourner le token JWT dans la réponse
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        try {
            // Vérifier d'abord si l'email existe dans la base de données
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + request.getEmail()));

            // Si l'email existe, procéder à l'authentification
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Générer le token JWT pour l'utilisateur authentifié
            String jwtToken = jwtService.generateToken(new HashMap<>(), user);
//
            // Construire et retourner la réponse
            return new AuthenticationResponse(jwtToken, user.getFirstName(), user.getLastName(), user.getEmail(),user.getIdUser());
        } catch (UsernameNotFoundException e) {
            // Gestion spécifique lorsque l'email n'est pas trouvé
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (BadCredentialsException e) {
            // Gestion spécifique lorsque le mot de passe est invalide
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        } catch (Exception e) {
            // Gestion des autres erreurs
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Authentication failed");
        }
    }


    //hedi te5dem jawha bahi
/*
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
        */

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String userInfoUri;
    @Autowired

    private OAuth2AuthorizedClientManager authorizedClientManager;




    public String exchangeCodeForUser(String code, String redirectUri) {
        try {
            OAuth2AuthorizedClient authorizedClient = authorizeClient(code, redirectUri);
            if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
                throw new IllegalStateException("Failed to get access token");
            }

            OAuth2User oAuth2User = fetchUserFromGoogle(authorizedClient.getAccessToken());
            User user = saveNewGoogleUser(oAuth2User);


            // Populate claims as needed
            var jwtToken = jwtService.generateToken(new HashMap<>(), user);// Assuming generateToken accepts user email or claims map

            return jwtToken;
        } catch (Exception e) {
            throw new RuntimeException("Failed to exchange code for user", e);
        }
    }

    private OAuth2User fetchUserFromGoogle(OAuth2AccessToken accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        // Utiliser directement le jeton d'accès pour faire la requête à l'API Google
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken.getTokenValue()); // Utilisation du Bearer Token

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoUri, // L'URL pour récupérer les informations de l'utilisateur, typiquement fourni dans la configuration
                HttpMethod.GET,
                entity,
                Map.class);

        Map<String, Object> userInfo = response.getBody();
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                userInfo,
                "name"); // Le "name" est l'attribut pour identifier le nom d'utilisateur principal dans les informations retournées
    }



    public OAuth2AuthorizedClient authorizeClient(String code, String redirectUri) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("google")
                .principal("ANONYMOUS") // Use an appropriate principal
                .attributes(attrs -> {
                    attrs.put(OAuth2ParameterNames.CODE, code);
                    attrs.put(OAuth2ParameterNames.REDIRECT_URI, redirectUri);
                }).build();

        return this.authorizedClientManager.authorize(authorizeRequest);
    }

    private User saveNewGoogleUser(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstName(oAuth2User.getAttribute("given_name"));
            newUser.setLastName(oAuth2User.getAttribute("family_name"));
            newUser.setRole("USER"); // Replace with your desired default role
            newUser.setStatue(true); // Set initial status

            // Additional attributes
            return userRepository.save(newUser);
        });
    }
    }

