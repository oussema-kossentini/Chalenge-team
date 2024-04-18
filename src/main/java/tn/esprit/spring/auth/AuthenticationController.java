package tn.esprit.spring.auth;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;
//
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.UserService;

import java.io.IOException;
import java.util.*;

import javax.ws.rs.NotFoundException;

import static tn.esprit.spring.entities.Role.USER;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private  AuthenticationService  service;

    @Autowired
    private   JwtService jwtService;
    @Autowired
  private    ObjectMapper objectMapper; // Jackson's ObjectMapper
   // private Logger logger;

    //@Autowired
    //private AuthenticationController authentication;

/*
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }
*/
/*@GetMapping("/userinfo/{idUser}")
public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable String idUser, @RequestHeader("Authorization") String token) {
    // Validate JWT token
    if (!jwtService.isLoggedInAndJwtValid(token)) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Extract username from token
    String username = jwtService.extractUsername(token);

    // Find user by id
    User user = userRepository.findById(idUser)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Check if the user is authorized to access this resource based on their role
    UserDetails userDetails = getUserDetailsFromToken(token);
    if (!isUserAuthorized(userDetails)) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // Build response
    UserInfoResponse userInfoResponse = UserInfoResponse.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getUsername())
            .dateOfBirth(user.getDateOfBirth())
            .nationality(user.getNationality())
            .phone(user.getPhone())
            .profilePicture(user.getProfilePicture())
            .build();

    return ResponseEntity.ok(userInfoResponse);
}
*/

  /*  private UserDetails getUserDetailsFromToken(String token) {
        // Implement this method to extract UserDetails from token
        // You can use JwtService or any other mechanism based on your implementation
    }

    private boolean isUserAuthorized(UserDetails userDetails) {
        // Implement this method to check if the user has the necessary role to access this resource
        // You can extract roles from UserDetails and perform the authorization logic based on your requirements
    } */


  @PutMapping("/ate-image")
  //hedi mtaa el role  lazem menha
  //tzid authentication fel methode
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    public ResponseEntity<String> updateProfileImage(Authentication authentication, @RequestParam("image") MultipartFile file) throws IOException {
      if (file == null || file.isEmpty()) {
          return ResponseEntity.badRequest().body("No file provided");
      }
      byte[] fileBytes = file.getBytes();
      System.out.println("Received file with size: " + fileBytes.length + " bytes");
      /*heda hezou */
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        /*star el fouk hezou */

        user.setProfilePicture(file.getBytes());
        userRepository.save(user);

        return ResponseEntity.ok("Profile image updated successfully");
    }

    //methode khir maghir id ou taksir ras kenek autehntifier ou andek les role marhbe bik
    /* angular
     */
    /*fetchUserInfo(token: string) {
  const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  const url = '/api/userinfo'; // Adjust the URL to match your actual endpoint

  return this.http.get<any>(url, { headers }).pipe(
    tap(userInfo forEach(key => {
    if (userInfo[key] != null) {
      console.log(`Storing ${key}: ${userInfo[key]}`);  // Log for debugging
      localStorage.setItem(key, userInfo[key]);
    } else {
      localStorage.removeItem(key);
    }
  });
    catchError(error => {
      console.error('Error fetching user info', error);
      throw error;
    })
  );
}
*/
/*spring*/

    /*@GetMapping("/userinfo")
@PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
public ResponseEntity<UserInfoResponse> getUserInfo(Authentication authentication) {
    try {
        // Cast the authentication principal to your custom UserDetails if necessary
        String userId = ((ExtendedUser)authentication.getPrincipal()).getId();

        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.debug("Attempting to find user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        logger.info("User found: {}", user.getUsername());
        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getUsername()) // Check if getUsername() should rather be getEmail()
                .dateOfBirth(user.getDateOfBirth())
                .nationality(user.getNationality())
                .phone(user.getPhone())
                .profilePicture(user.getProfilePicture())
                .build();
        return ResponseEntity.ok(userInfoResponse);
    } catch (NotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (ClassCastException e) {
        // Handle case where the cast is incorrect
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
*/
  @GetMapping("/userinfo/{idUser}")
 // @PreAuthorize("hasAuthority('ADMINISTRATOR')")

  @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")

  // @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER') or hasRole('TEACHER') or hasRole('STUDENT') or hasRole('PROFESSOR')  ")
  public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable String idUser, Authentication authentication) {
      try {
          Logger logger; // Initialisation du logger
          logger = LoggerFactory.getLogger(this.getClass());
          logger.debug("Attempting to find user with ID: {}", idUser);
          User user = userRepository.findById(idUser)
                  .orElseThrow(() -> new NotFoundException("User not found"));
          logger.info("User found: {}", user.getUsername());
          UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                  .firstName(user.getFirstName())
                  .lastName(user.getLastName())
                  .email(user.getUsername())
                  .dateOfBirth(user.getDateOfBirth())
                  .nationality(user.getNationality())
                  .phone(user.getPhone())
                  .profilePicture(user.getProfilePicture())
                  .build();
          return ResponseEntity.ok(userInfoResponse);
      } catch (NotFoundException e) {
          // Gérer l'exception NotFoundException ici
          return ResponseEntity.notFound().build();
      }
  }


   /* public ResponseEntity<UserInfo> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        // Récupérer l'ID de l'utilisateur à partir des informations d'authentification
        Long userId = ((CustomUserDetails) userDetails).getId();

        // Récupérer les informations de l'utilisateur à partir de l'ID et les renvoyer
        UserInfo userInfo = userRepository.findById(userId).orElse(null);
        return ResponseEntity.ok(userInfo);
    }
*/

@PostMapping("/forgot-password")
public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
    String email = payload.get("email");
    boolean result = service.requestPasswordReset(email);
    if (result) {
        return ResponseEntity.ok().body("Reset password email sent.");
    }
    return ResponseEntity.badRequest().body("Email not found.");
}
   /* @RequestMapping(value = "/update-password-after-reset", method = RequestMethod.OPTIONS)
    public HttpStatus handleOptionsRequest() {
        // Autorisez les requêtes OPTIONS pour cette URL
        return HttpStatus.OK;
    }*/
    @PostMapping("/update-password-after-reset")
    public ResponseEntity<?> updateUserPassword(@RequestBody PasswordUpdateRequest  request) {
        boolean updateStatus = service.updatePassword(request.getEmail(), request.getNewPassword());

        if (updateStatus) {
            return ResponseEntity.ok().body("Mot de passe mis à jour avec succès");
        } else {
            return ResponseEntity.badRequest().body("La mise à jour du mot de passe a échoué");
        }

    }
    @PostMapping("/verify-reset-code")
    public ResponseEntity<?> verifyResetCode(@RequestParam("email") String email, @RequestParam("resetToken") String resetToken) {
        boolean isValid = service.verifyResetCode(email,resetToken);
        if (isValid) {
            return ResponseEntity.ok().body("Code verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid code.");
        }
    }


    @Autowired
    private GoogleAuthService googleAuthService;

   /* @PostMapping("/google")
    public ResponseEntity<?> authenticateUserWithGoogle(@RequestBody Map<String, String> codePayload) {
        String code = codePayload.get("code");

        if(code!= null ){

        System.out.println("code is recupered ");
        }
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().body("Code is missing");
        }
        try {
            User user = googleAuthService.processGoogleUser(code);
            // Générez le JWT pour l'utilisateur (omis pour la brièveté)
            var jwtToken = jwtService.generateToken(new HashMap<>(), user);

            return ResponseEntity.ok(Map.of(
                    "jwtToken", jwtToken,
                    "user", user
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to authenticate with Google");
        }



    }*/



   /* @PostMapping("/google")
    public ResponseEntity<?> authenticateUserWithGoogle(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().body("Le code est requis.");
        }

        try {
            // Remplacez "YOUR_REDIRECT_URI" par l'URI de redirection configuré dans votre client OAuth2 Google
            String jwtToken = service.exchangeCodeForUser(code, "http://localhost:4200/google-callback");
            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
        } catch (Exception e) {
            // Gérer les exceptions spécifiques si nécessaire
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'échange du code: " + e.getMessage());
        }
    }
*/
  /* @PostMapping(value = "/register", consumes = "multipart/form-data")
   public ResponseEntity<AuthenticationResponse> register(
           @RequestPart("request") @Valid RegisterRequest request,
           @RequestPart(value = "profilePicture", required = false) MultipartFile image) {

       User user = new User();
       user.setFirstName(request.getFirstName());
       user.setLastName(request.getLastName());
       user.setEmail(request.getEmail());
       user.setPassword(passwordEncoder.encode(request.getPassword()));
       user.setDateOfBirth(request.getDateOfBirth());
       user.setNationality(request.getNationality());
       user.setPhone(request.getPhone());
       user.setStatue(true); // Assuming the field should be 'status'?
       user.setRole(String.valueOf(request.getRole())); // Make sure to set the role appropriately

       // Service method to handle registration
    //   AuthenticationResponse response = service.addUserWithImage(user, image);
       userRepository.save(user);
       return ResponseEntity.ok(response);
   }*/

    /*test lel sswagger */
   private final PasswordEncoder passwordEncoder;

  /*  @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<?> register(
            @RequestPart("request") @Valid RegisterRequest request,
            @RequestPart(value = "profilePicture", required = false) MultipartFile image) {

        // Create and configure the user object
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        System.out.println(request.getPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Ensure passwordEncoder is injected
        user.setDateOfBirth(request.getDateOfBirth());
        user.setNationality(request.getNationality());
        user.setPhone(request.getPhone());
        user.setStatue(true); // Assuming the field should be 'status'
        user.setRole(String.valueOf(request.getRole()));

        // Handle image storage if provided
        if (image != null && !image.isEmpty()) {
            try {
                byte[] imageBytes = image.getBytes();
                // Here you would typically use a service to save the image bytes to MongoDB
                user.setProfilePicture(imageBytes); // Assuming User class has a byte array field for the image
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error saving image");
            }
        }

        // Save user to the repository
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user");
        }

        // Generate JWT token after saving the user
        var userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>());
        String jwtToken = jwtService.generateToken(new HashMap<>(), userDetails); // Ensure jwtService is injected

        // Build and return the authentication response
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .idUser(user.getId().toString()) // Assuming getId() exists
                .build();

        return ResponseEntity.ok(response);
    }
*/

    @GetMapping("/roleuser")
    public ResponseEntity<String> recupererRoleUser(Authentication authentication) {
        String userEmail = ((User) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return ResponseEntity.ok(user.getRole().name());
    }
    @PutMapping("/updateStatus")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<String> updateBanStatus(@RequestParam String userEmail, @RequestParam boolean status) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        user.setStatue(status);
        userRepository.save(user);

        return ResponseEntity.ok("User ban status updated to: " + status);
    }
    @PutMapping("/affecterRole")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<String> affecterRoleUser(
            @RequestParam("userEmail") String userEmail,
            @RequestParam("userRole") String userRole,
            Authentication authentication) {


        // Trouver l'utilisateur par son email
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // Essayer de modifier le rôle de l'utilisateur
        try {
            System.out.println("test1");
            Role role = Role.valueOf(userRole.toUpperCase()); // Convertir en majuscules pour éviter les erreurs de casse
            System.out.println("test");
            user.setRole(role);
            userRepository.save(user); // Mise à jour de l'utilisateur dans la base de données
            return ResponseEntity.ok("Role updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role provided");
        }
    }

//
    // @PreAuthorize("hasAuthority('ADMINISTRATOR')  || hasAuthority('TEACHER')  || hasAuthority('PROFESSOR')")
@Autowired
    UserService userService;
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || hasAnyAuthority('USER', 'TEACHER', 'STUDENT', 'PROFESSOR')")
    @PutMapping("/modifyInfoUserConnected")
    public ResponseEntity<User> modifyUser(Authentication authentication, @RequestBody User updatedUser) {
        String userEmail = authentication.getName();
        User user = userService.modifyUserCN(userEmail, updatedUser);
        return ResponseEntity.ok(user);
    }
  @PostMapping(value = "/register", consumes = "multipart/form-data")
  public ResponseEntity<?> register(

          @RequestPart("request") @Valid @ModelAttribute RegisterRequest request,
          @RequestPart(value = "profilePicture", required = false) MultipartFile image) {

    // Check for existing email
      if ( userRepository.findByEmail(request.getEmail()).isPresent()) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                  .body("Error: Email address already in use.");
      }

      // Create and configure the user object
      User user = new User();
      user.setFirstName(request.getFirstName());
      user.setLastName(request.getLastName());
      user.setEmail(request.getEmail());
      user.setPassword(passwordEncoder.encode(request.getPassword()));
      user.setDateOfBirth(request.getDateOfBirth());
      user.setNationality(request.getNationality());
      user.setPhone(request.getPhone());
      user.setStatue(true); // Assuming the field should be 'status'

     // user.setRole(request.getRole() == null ? Role.USER : request.getRole());
      // Assuming you get role as String from RegisterRequest; otherwise adjust accordingly
     user.setRole(request.getRole());

      // Handle image storage if provided
      if (image != null && !image.isEmpty()) {
          try {
              byte[] imageBytes = image.getBytes();
              // Implement service to save image bytes (MongoDB or file system)
              user.setProfilePicture(imageBytes);
          } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                      .body("Error saving image: " + e.getMessage());
          }
      }

      // Save user to the repository
      try {
          userRepository.save(user);
      } catch (DataIntegrityViolationException e) {
          // Handle specific database constraint violation errors
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                  .body("Error registering user: " + e.getMessage());
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body("Error registering user: " + e.getMessage());
      }

      // Generate JWT token
    /*  var userDetails = new org.springframework.security.core.userdetails.User(
              user.getEmail(), user.getPassword(), new ArrayList<>());
      String jwtToken = jwtService.generateToken(new HashMap<>(), userDetails);*/
      var jwtToken = jwtService.generateToken(new HashMap<>(), user);

      // Retourner le token JWT dans la réponse
    //  return AuthenticationResponse.builder().token(jwtToken).build();
      // Build and return the response
      AuthenticationResponse response = AuthenticationResponse.builder()
              .token(jwtToken)
              .firstName(user.getFirstName())
              .lastName(user.getLastName())
              .email(user.getEmail())
              .idUser(user.getId().toString())
              .build();

      return ResponseEntity.ok(response);
  }

    @PostMapping("/facebook")
    public ResponseEntity<AuthenticationResponse> signInWithFacebook(@RequestBody String accessToken) {
        // For demonstration purposes, let's assume the authentication is successful
        AuthenticationResponse response = new AuthenticationResponse();
        System.out.println("Access Token received from Facebook: " + accessToken);

        try {
            // Use the Facebook Graph API to retrieve user information
            String userInfoUrl = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map<String, Object>> userInfoResponse = restTemplate.exchange(
                    userInfoUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                System.out.println("User Info received from Facebook: " + userInfoResponse);
                Map<String, Object> userInfo = userInfoResponse.getBody();
                System.out.println("User Info received from Facebook: " + userInfo);
                String email = (String) userInfo.get("email");
                String name = (String) userInfo.get("name");

                // Check if user exists with this email
                User user = userRepository.findByEmail(email).orElse(null);
                if (user == null) {
                    // Create new user
                    user = new User();
                    user.setEmail(email);
                    user.setFirstName(name);
                    user.setRole(Role.USER);
                    user.setPassword(passwordEncoder.encode("facebook")); // Set default password for Facebook users
                    user.setStatue(true);
                    userRepository.save(user);
                }

                // Generate JWT token
                String jwtToken = jwtService.generateToken(new HashMap<>(), user);

                response.setToken(jwtToken);
                response.setEmail(email);
                response.setFirstName(name);

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to authenticate with Facebook");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to authenticate with Facebook");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/google")
    public ResponseEntity<AuthenticationResponse> signInWithGoogle(  @RequestBody String token){
        // For demonstration purposes, let's assume the authentication is successful
        AuthenticationResponse response = new AuthenticationResponse();
        System.out.println("Token received from Google: " + token);

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList("896713448835-jmsg22uggotbi02tm66voprbv75ruqih.apps.googleusercontent.com")) // Replace with your Google client ID
                    .build();

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("given_name");
                String familyName = (String) payload.get("family_name");

                //check if user exist with this email
                User user = userRepository.findByEmail(email).orElse(null);
                if(user == null){
                    //create new user
                    user = new User();
                    user.setEmail(email);
                    user.setFirstName(name);
                    user.setLastName(familyName);
                    user.setRole(Role.USER);
                    user.setPassword(passwordEncoder.encode("google"));
                    user.setStatue(true);
                    userRepository.save(user);
                }

                //generate jwt token
                String jwtToken = jwtService.generateToken(new HashMap<>(), user);

                response.setToken(jwtToken);
                response.setEmail(email);
                response.setFirstName(name);
                response.setLastName(familyName);


            }

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to authenticate with Google");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticateGoogle")
    public ResponseEntity<AuthenticationResponse> authenticateGoogle(@RequestBody AuthenticateRequest request) {
        // Appel au service pour effectuer l'authentification
        try {
            AuthenticationResponse response = service.authenticateWithGoole(request.email);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            // Propagation de l'exception gérée dans le service
            throw e;
        }
    }

    /*fook le swagger */
/*te5dem angular louta*/
  /* @PostMapping(value = "/register", consumes = "multipart/form-data")
public ResponseEntity<AuthenticationResponse> register(
        @ModelAttribute @Valid RegisterRequest request,
        @RequestPart(value = "profilePicture", required = false) MultipartFile image) {
    User user = new User();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword()); // Hacher le mot de passe
        user.setPassword(encodedPassword); // Définir le mot de passe haché
      //  user.setStatue(true);
    user.setDateOfBirth(request.getDateOfBirth());
    user.setNationality(request.getNationality());
    user.setPhone(request.getPhone());
    user.setStatue(true); // Définir le statut sur true par défaut
 // Définir le rôle sur USER par défaut
    user.setRole("");

    // Call your service to handle the registration logic
    AuthenticationResponse response = service.addUserimage(user, image);

    // Return the response entity
    return ResponseEntity.ok(response);
}*/
   // private static final Logger log = (Logger) LoggerFactory.getLogger(AuthenticationService.class);
   // @PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        if (authToken != null && authToken.startsWith("Bearer ")) {
            String jwtToken = authToken.substring(7);
            // Appeler votre service pour ajouter le jeton à la liste noire
            jwtService.blacklistToken(jwtToken);
           // log.info("Token blacklisted, user logged out successfully");
            return ResponseEntity.ok(Collections.singletonMap("message", "User logged out successfully"));
        } else {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error", "Invalid Authorization header");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
@Autowired
    UserRepository userRepository;
    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusRequest updateStatusRequest, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Extrait le token du header Authorization
        String token = authorizationHeader.substring(7);
        String email = jwtService.getEmailFromToken(token);

        // Utilisez l'email pour trouver l'utilisateur
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get(); // Obtenez l'utilisateur si présent
            user.setEtatDeConexion(updateStatusRequest.getStatus()); // Mettez à jour l'état de l'utilisateur
            userRepository.save(user); // Sauvegardez les changements
            // Ajoutez un message personnalisé à la réponse
            Map<String, String> response = new HashMap<>();
            response.put("message","Le statut de connexion a été mis à jour avec succès.");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("/is-logged-in")
    public ResponseEntity<Boolean> isLoggedInAndJwtValid(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }

        String jwtToken = authorizationHeader.substring(7); // Retirez "Bearer " pour obtenir le JWT uniquement
        boolean isTokenValid = jwtService.isLoggedInAndJwtValid(jwtToken); // Utilisez votre service pour vérifier la validité du token
        if (isTokenValid) {
            return ResponseEntity.ok(true);
        } else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null || principal.getAttributes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Retourne les détails de l'utilisateur connecté
        return ResponseEntity.ok(principal.getAttributes());
    }

    //jawha bahi ama lazem maaha 5idma
   /* @PostMapping("/authenticate")
    //@PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    ){

        return ResponseEntity.ok(service.authenticate(request));

    }


    */

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest request) {
        // Appel au service pour effectuer l'authentification
        try {
            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            // Propagation de l'exception gérée dans le service
            throw e;
        }
    }

}



//test