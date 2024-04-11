package tn.esprit.spring.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.spring.entities.Nationality;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private String firstName;
    private String lastName;
    private String email; //
    private String idUser;
    private String password;
    private String dateOfBirth;
    private Nationality nationality;
    private String phone;
    @JsonProperty
    private  byte[] profilePicture;

}
