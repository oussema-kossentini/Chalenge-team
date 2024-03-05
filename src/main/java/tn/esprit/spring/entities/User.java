package tn.esprit.spring.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.*;
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
    String idUser;
    String firstName;
    String lastName;
    String email;
    String password;
    Date dateOfBirth;
   
    @Field(targetType = FieldType.STRING)
    Nationality nationality;
    Long phone;
    boolean statue;
    @Field(targetType = FieldType.STRING)
    Role role;

   @DBRef
  Set<Specialite>specialites;

    @DBRef
     Set<Publication> publications;
}
