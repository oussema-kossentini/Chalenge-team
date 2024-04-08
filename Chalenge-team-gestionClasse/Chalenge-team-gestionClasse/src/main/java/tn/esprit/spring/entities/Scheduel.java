package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class Scheduel implements Serializable {
    @Id
    String idScheduel;
    String title;
    Date startDate;
    Date  endDate;
    @DBRef
    private Classe classe;
    @DBRef
    @JsonManagedReference
    private Set<Session> sessions;
    public Set<Session> getSessions() {
        if (sessions == null) {
            sessions = new HashSet<>();
        }
        return sessions;
    }
}
