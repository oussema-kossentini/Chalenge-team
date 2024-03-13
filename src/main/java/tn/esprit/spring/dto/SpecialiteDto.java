package tn.esprit.spring.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Specialite;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter @Setter
public class SpecialiteDto {


    String title;

    List<String> classes = new ArrayList<>();


}
