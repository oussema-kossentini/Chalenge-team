package tn.esprit.spring.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Classe implements Serializable {
    @Id
    String idClasse;
    String nameClasse;

    @Field(targetType = FieldType.STRING)
    String level; // Assurez-vous que ce champ est traité comme une chaîne dans MongoDB.

    Date universityDate;
   // private String specialiteTitle;
    // Autres champs et méthodes de la classe

    //public void setSpecialiteTitle(String title) {
     //   this.specialiteTitle = title;
    //}

    private Set<String> usersIds = new HashSet<>(); // Initialisation directe


    @DBRef
    private Specialite specialite;



    @DBRef
    private Scheduel scheduel;

    @DBRef
    Set<Evaluation> evaluations;
    @DBRef
    Set<User>users;
}
