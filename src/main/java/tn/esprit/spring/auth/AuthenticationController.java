package tn.esprit.spring.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.User;

import java.io.IOException;

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


@PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
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

    @PreAuthorize("hasRole('ADMINSTRATOR') or hasRole('STUDENT') or hasRole('PROFESSOR')")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }
}
