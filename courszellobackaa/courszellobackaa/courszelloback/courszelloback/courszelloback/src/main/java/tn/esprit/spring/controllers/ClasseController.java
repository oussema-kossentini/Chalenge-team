package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.services.IClasseService;
import tn.esprit.spring.services.ICourseService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/classes")
public class ClasseController {

    IClasseService classeService;
    @PostMapping("add/classe")
    public Classe addinClasse(@RequestBody Classe us){

        return classeService.addClasse(us);
    }
    @GetMapping("/retrieve-all-classes")
    public List<Classe> getClasses() {
        List<Classe> listClasses = classeService.retrieveAllClasses();
        return listClasses;
    }
    @DeleteMapping("/remove-classes/{classe-id}")
    public void removeChambre(@PathVariable("classe-id") String chId) {
        classeService.removeClasse(chId);
    }
    @PutMapping("/modify-classe/{id}")
    public Classe modifyChambre(@PathVariable String id,@RequestBody Classe c) {
        c.setIdClasse(id);
        return classeService.addClasse(c);

    }
}
