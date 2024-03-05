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
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Publication implements Serializable {
    @Id
    String idPublication;
    String content;
    Date creationDate;
    boolean like;
    @DBRef
    private User user;

    @DBRef
    private Set<Comment> comments;

}
