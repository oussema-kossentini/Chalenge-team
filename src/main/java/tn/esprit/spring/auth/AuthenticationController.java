package tn.esprit.spring.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.User;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService  service;

    private final  JwtService jwtService;
  private   final ObjectMapper objectMapper; // Jackson's ObjectMapper

/*
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
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
        boolean isValid = service.verifyResetCode(email, resetToken);
        if (isValid) {
            return ResponseEntity.ok().body("Code verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid code.");
        }
    }


@PostMapping(value = "/register", consumes = "multipart/form-data")
public ResponseEntity<AuthenticationResponse> register(
        @RequestPart("request") RegisterRequest request,

        @RequestPart(value = "profilePicture", required = false) MultipartFile image) throws IOException {

    try {
        if (image != null && !image.isEmpty()) {
            byte[] profilePictureBytes = image.getBytes();
            request.setProfilePicture(profilePictureBytes);
        }

    } catch (IOException e) {
        throw new RuntimeException(e);
    }


    // Call your service to handle the registration logic
    AuthenticationResponse response = service.register(request, image);

    // Return the response entity
    return ResponseEntity.ok(response);
}
    @PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        if (authToken != null && authToken.startsWith("Bearer ")) {
            String jwtToken = authToken.substring(7);
            // Appeler votre service pour ajouter le jeton à la liste noire
            jwtService.blacklistToken(jwtToken);
            return ResponseEntity.ok().body("User logged out successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }
    }


    @PostMapping("/authenticate")

    //@PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }
}
