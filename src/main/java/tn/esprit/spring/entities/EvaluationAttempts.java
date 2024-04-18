package tn.esprit.spring.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationAttempts {

    @Id
    String evaluationAttemptsId;
    @DBRef
    User userId;
    @DBRef
    Evaluation evaluation;
    long totalScore;

}