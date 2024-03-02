package tn.esprit.spring.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)

public class QA implements Serializable {

    String idQa;
    String question;

    @Field(targetType = FieldType.STRING)

    Categorie categorie;
    @Field("response")
    private List<String> response;

    @DBRef
    Evaluation evaluation;


}
