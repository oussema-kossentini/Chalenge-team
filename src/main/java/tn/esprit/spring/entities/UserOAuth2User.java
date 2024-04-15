package tn.esprit.spring.entities;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import tn.esprit.spring.entities.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
public class UserOAuth2User implements OAuth2User {


    private User user;
    private OAuth2User oauth2User;
    public UserOAuth2User(User user, Map<String, Object> attributes) {
        this.user = user;
        this.oauth2User = new DefaultOAuth2User(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())), attributes, "name");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Adaptez cette méthode pour retourner les autorités/roles de l'utilisateur si nécessaire
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public User getUser() {
        return user;
    }

}
