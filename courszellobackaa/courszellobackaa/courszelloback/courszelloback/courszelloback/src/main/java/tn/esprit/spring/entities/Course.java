package tn.esprit.spring.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

public class Course implements Serializable {
    @Id
    String idCourse;
    String title;
    String description;
    Date debutDate;
    Date endDate;
    int level;
    @DBRef
    Subject subject;

    @DBRef
    Set<Content> contents;

 /*   @DBRef
    private List<Classe> classes;

   /*@ManyToOne
    private User hadir;*/
}