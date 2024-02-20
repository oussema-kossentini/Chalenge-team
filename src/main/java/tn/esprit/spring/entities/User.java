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


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User implements Serializable {
    @Id
    @JsonIgnore
    String idUser;
    String firstName;
    String lastName;
    String email;
    String password;
    //byte[] PDP;
    // @Field(targetType = FieldType.BINARY)


@JsonIgnore
   byte[] profilePicture;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateOfBirth;
    //photo de profile


    @Field(targetType = FieldType.STRING)
    Nationality nationality;
    String phone;
    boolean statue;
    @Field(targetType = FieldType.STRING)
    Role role;

   @DBRef
    Classe classe;

    @DBRef
     Set<Publication> publications;
}
