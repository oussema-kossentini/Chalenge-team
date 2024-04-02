package tn.esprit.spring.dto;
import  lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter @Setter
public class SpecialiteDto {
    String title;

    List<String> classes = new ArrayList<>();
}
