package tn.esprit.spring.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tn.esprit.spring.configuration.JwtService;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.entities.UserOAuth2User;
import tn.esprit.spring.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {



    @Autowired
    private UserRepository userRepository;

@Autowired
private JwtService jwtService;
   /* @Autowired
    private PasswordEncoder passwordEncoder;

    */


  //  @Override

 /*   public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseGet(() -> new User());
        user.setEmail(email);
        // Définir d'autres propriétés à partir des attributs si nécessaire

        userRepository.save(user);

        // Retourner un wrapper ou une implémentation de OAuth2User contenant les données de l'utilisateur
        return new UserOAuth2User(user, oauth2User);
    }
*/


    //fiha mochkla
    /*
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");
        String pictureUrl = (String) attributes.get("picture");

        // Convertissez l'URL de l'image en un tableau de bytes si nécessaire.
        // Cela dépend de la façon dont vous voulez stocker l'image de profil.
      //  byte[] profilePicture = ...; // Faites une requête HTTP pour obtenir l'image et la convertir en byte[], ou stockez l'URL selon vos besoins.

        User user = userRepository.findByEmail(email)
                .map(existingUser -> {
                    existingUser.setFirstName(firstName);
                    existingUser.setLastName(lastName);
                   // existingUser.setProfilePicture(profilePicture); // Assurez-vous de convertir l'URL de l'image en byte[] si c'est comme ça que vous le stockez.
                    return existingUser;
                })
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFirstName(firstName);
                    newUser.setLastName(lastName);
                  //  newUser.setProfilePicture(profilePicture); // Assurez-vous de convertir l'URL de l'image en byte[] si c'est comme ça que vous le stockez.
                    newUser.setRole(""); // ou un autre rôle par défaut selon vos besoins
                    newUser.setStatue(true); // Ou toute autre logique d'activation de compte
                    // Settez d'autres champs comme dateOfBirth, nationality, etc. si nécessaire.
                    return newUser;
                });

        userRepository.save(user);

        return oauth2User; // Retournez OAuth2User pour respecter le contrat de la méthode surchargée.
    }
*/
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return processOAuth2User(user);
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {
        // Extrait les détails de l'utilisateur depuis l'objet OAuth2User
        String email = oAuth2User.getAttribute("email");
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            // Créez un nouvel utilisateur si nécessaire
            user = new User();
            user.setEmail(email);
            user.setFirstName(oAuth2User.getAttribute("name"));
user.setStatue(true);
//user.setRole("");
            userRepository.save(user);
        }
        return new UserOAuth2User(user, oAuth2User.getAttributes());
    }

}