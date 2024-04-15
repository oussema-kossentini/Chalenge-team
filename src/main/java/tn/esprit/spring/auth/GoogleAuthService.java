package tn.esprit.spring.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;

import java.util.Map;

@Service
public class GoogleAuthService {

    @Autowired
    private UserRepository userRepository;

    // Injectez ou définissez vos variables pour clientId, clientSecret et redirectUri

   /* public User processGoogleUser(String code) {
        // Échangez le code contre un token d'accès et des informations utilisateur via Google API
      /*  RestTemplate restTemplate = new RestTemplate();
        String accessTokenUrl = "https://oauth2.googleapis.com/token";
        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

        // Construisez la requête pour obtenir le token d'accès (omis pour la brièveté)

        // Utilisez le token d'accès pour obtenir des informations utilisateur de Google
        Map<String, String> userInfo = restTemplate.getForObject(userInfoUrl, Map.class);
*/
      /*  RestTemplate restTemplate = new RestTemplate();

        // Préparer la requête pour obtenir le token d'accès
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "157192878397-o2j22lj2i0uaacgdvr3js3at39lclltg.apps.googleusercontent.com");
        map.add("client_secret","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");
        map.add("code", code);
        map.add("redirect_uri","http://localhost:4200/google-callback");
        map.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
       // String s = "AIzaSyCNs-cIS0PsmJew0tKu7gproc2WXfRKe4Y";
        String googleapitoken= "AIzaSyCNs-cIS0PsmJew0tKu7gproc2WXfRKe4Y";
        ResponseEntity<Map> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token", request, Map.class);
        String accessToken = (String) response.getBody().get("access_token");

        // Utiliser le token d'accès pour obtenir des informations utilisateur
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(userInfoHeaders);

        ResponseEntity<Map> userInfoResponse = restTemplate.exchange("https://www.googleapis.com/oauth2/v2/userinfo", HttpMethod.GET, entity, Map.class);
        Map<String, String> userInfo = userInfoResponse.getBody();
        // Extraire les informations nécessaires
        String email = userInfo.get("email");
        String firstName = userInfo.get("given_name");
        String lastName = userInfo.get("family_name");
        // Assumez que vous avez des méthodes pour convertir l'image URL en byte[] si nécessaire

        // Vérifiez si l'utilisateur existe déjà et le mettez à jour ou créez-en un nouveau
        User user = userRepository.findByEmail(email).orElse(new User());
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole("");
        user.setStatue(true);
        // Settez d'autres champs si nécessaire

        return userRepository.save(user);
    }


   /* public boolean validateToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String validateUrl = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + accessToken;
        try {
            Map<String, String> response = restTemplate.getForObject(validateUrl, Map.class);
            String aud = response.get("aud");
            String iss = response.get("iss");
            if ("157192878397-o2j22lj2i0uaacgdvr3js3at39lclltg.apps.googleusercontent.com".equals(aud) && ("https://accounts.google.com".equals(iss) || "accounts.google.com".equals(iss))) {
                return true; // Token is valid
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Token is invalid
    }*/


}