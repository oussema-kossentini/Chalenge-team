package tn.esprit.spring.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Evaluation implements Serializable {
    @Id
    String idEvaluation;
    String title;
    String description;
    @Field(targetType = FieldType.STRING)
    TypeEvaluation typeEvaluation;
    Date startDate;
    Date endDate;
    boolean statue;
    Duration duration;
    @DBRef
    Classe classe;
    @DBRef
    List<QA> qas;
    @DBRef
    List<Grade>grades;
}
