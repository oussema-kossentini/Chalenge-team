package tn.esprit.spring.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;
/*
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        // Ici, vous pouvez utiliser l'email ou un identifiant unique de oauth2User pour retrouver votre utilisateur dans la base de données
        String email = oauth2User.getAttribute("email");
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String jwtToken = jwtService.generateToken(new HashMap<>(), user);

            // Configurer la réponse pour inclure le JWT
            response.addHeader("Authorization", "Bearer " + jwtToken);

            // Vous pouvez également rediriger l'utilisateur ou retourner une réponse JSON avec le token et les informations de l'utilisateur
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé si nécessaire
        }
    }
*/

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId()); // Exemple de claim personnalisé, ajustez selon vos besoins
            // Générez le JWT en utilisant les claims et les détails de l'utilisateur
            String jwtToken = jwtService.generateToken(claims, user);

            response.setContentType("application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Map.of(
                    "jwtToken", jwtToken,
                    "message", "Google user is added to our database and is connected successfully"
            )));
            response.flushBuffer();
        } else {
            // Gérez le cas où l'utilisateur n'est pas trouvé dans la base de données
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found");
        }
    }


}
