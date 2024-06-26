package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User implements UserDetails,Serializable {
    @Id
    //@JsonIgnore
    String idUser;

    String firstName;
    String lastName;
    String email;
    String password;
    //byte[] PDP;
    // @Field(targetType = FieldType.BINARY)



    byte[] profilePicture;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    String dateOfBirth;
    //photo de profile


    @Field(targetType = FieldType.STRING)
    Nationality nationality;
    String phone;
    boolean statue;
    @Field(targetType = FieldType.STRING)
    Role role;
    String resetToken;
    Etat_DeConexion etatDeConexion;
    LocalDateTime ResetTokenExpiration;

    @JsonIgnore
    @DBRef
    Classe classe;
    @DBRef
    Set<Specialite>specialites;
    @DBRef
    Set<Publication> publications;
    @DBRef
    private List<EvaluationAttempts> evaluationAttempts;
    @DBRef
    private List<Evaluation> evaluations;
   /* public Role getRole() {
        return this.role;
    }*/

    public Role getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convertir le rôle de l'utilisateur en GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.name()));

    }
    private  User user;

    @Override
    public String getUsername() {
        // Utiliser l'email comme identifiant de connexion
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return statue;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return statue;
    }

    public String getId() {
        return this.idUser;
    }
    public void setRole(Role role) {
        if (role == null) {
            this.role = Role.USER; // Default to USER if null
        } else {
            this.role = role;
        }
    }

    /*public void setRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            this.role = Role.USER; // Supposez que vous avez une valeur DEFAULT dans votre énumération Role
        } else {
            this.role = Role.valueOf(role.toUpperCase(Locale.ROOT));
        }
    }*/

    /*public void setRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            this.role = Role.USER; // Définit USER comme valeur par défaut pour les rôles non définis ou incorrects.
        } else {
            try {
                this.role = Role.valueOf(role.toUpperCase(Locale.ROOT)); // Tente de convertir la chaîne de caractères en énumération.
            } catch (IllegalArgumentException e) {
                this.role = Role.USER; // Attribue USER en cas d'erreur, par exemple si 'NULL' ou une valeur incorrecte est fournie.
            }
        }
    }*/

}