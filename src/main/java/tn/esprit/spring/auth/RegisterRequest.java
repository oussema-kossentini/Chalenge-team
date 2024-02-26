package tn.esprit.spring.auth;

import tn.esprit.spring.entities.Nationality;
import tn.esprit.spring.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfBirth;
    private Nationality nationality;
    private String phone;
    private Role role;
    private  byte[] profilePicture;
    private Boolean statue;
    // Autres champs selon votre entité User
}
