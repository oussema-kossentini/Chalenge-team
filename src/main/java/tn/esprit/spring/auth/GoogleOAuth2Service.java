package tn.esprit.spring.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleOAuth2Service {
/*
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String userInfoUri;
@Autowired

    private  OAuth2AuthorizedClientManager authorizedClientManager;


@Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

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
        */
    }




