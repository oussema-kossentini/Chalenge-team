package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subject implements Serializable {
   @Id
   String idSubject;
   String subjectName;

   @DBRef
 //  @JsonBackReference
   Set<Session> sessions;
    public Set<Session> getSessions() {
        if (sessions == null) {
            sessions = new HashSet<>();
        }
        return sessions;
    }
    @DBRef
     Set<Course> courses;
   /* @DBRef
    private Set<Classe> classes;*/
}
