package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.ContentType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(value= JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Publication implements Serializable {
    @Id
    String idPublication;
String title;
    String content;
    String imageToken; // Champ pour stocker le token JWT de l'image
    private Date creationDate;
    int likes;
    int dislikes;
    String fileName;
    private int shareCount; // Nombre de fois que la publication a été partagée


    @DBRef
    private User creator;

    @DBRef
    @JsonIgnore
    private Set<Comment> comments;
    @DBRef
    private List<User> dislikedByUsers;

    @DBRef
     private List<User> likedByUsers;


}
