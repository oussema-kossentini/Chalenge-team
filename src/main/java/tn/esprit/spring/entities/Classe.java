package tn.esprit.spring.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
public class Classe implements Serializable {
    @Id
    String idClasse;
    String nameClasse;
    String level;
    String domaine;
    Date universityDate;
    @DBRef
    private Specialite specialite;

    @DBRef
    private Set<Subject> subjects;

    @DBRef
    private Scheduel scheduel;


    @DBRef
    Set<Evaluation> evaluations;
}
