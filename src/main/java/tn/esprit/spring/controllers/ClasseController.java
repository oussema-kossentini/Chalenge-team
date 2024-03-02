package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Level;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.services.IClasseService;
import tn.esprit.spring.services.ICourseService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/classes")
@CrossOrigin(origins = "http://localhost:4200/**")
public class ClasseController {

    IClasseService classeService;
ClasseRepository classeRepository;
    @PostMapping("add/classe")
    public Classe addinClasse(@RequestBody Classe us) {

        return classeService.addClasse(us);
    }

    @GetMapping("/retrieve-all-classe")
    public List<Classe> getClasse() {
        log.info("Retrieving all classes");
        List<Classe> listClasse = classeService.retrieveAllClasses();
        log.info("Classes retrieved: {}", listClasse);
        return listClasse;
    }


    @DeleteMapping("/remove-classes/{classe-id}")
    public void removeChambre(@PathVariable("classe-id") String chId) {
        classeService.removeClasse(chId);
    }

    //@PutMapping("/modify-classe/{id}")
    //public Classe modifyChambre(@PathVariable String id, @RequestBody Classe c) {
       // c.setIdClasse(id);
      //  return classeService.addClasse(c);

   // }

    @PutMapping("/modify-classe")
    public ResponseEntity<Classe> modifyUser(@RequestBody Classe classe){

    Classe updatedUser = classeService.modifyClasse(classe); // Assuming there's a method in userService to modify the user
        return ResponseEntity.ok(updatedUser);
}

    @GetMapping("/level")
    public Level[] getLevels() {
        return Level.values();
    }


    @GetMapping("/{id}")
    public Classe gettingClasse(@RequestParam("classe-id") String idClasse){
        return classeService.getClasseById(idClasse);
    }

    @PostMapping("ajouter-affecter/{idSpecialite}")
    Classe ajouterFoyerEtAffecterAUniversite (@RequestBody Classe classe, @PathVariable String idSpecialite){
        return classeService.ajouterFoyerEtAffecterAUniversite(classe,idSpecialite);
    }


    @Operation(description = "récupérer toutes les Posts pour un user ")
    @GetMapping("/posts/{idSpecialite}")
    public ResponseEntity<List<Classe>> getPostsBySpecialite(@PathVariable("idSpecialite") String idSpecialite) {
        List<Classe> posts = classeService.retrievePostsByidUser(idSpecialite);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    //hedhom tjibli list mta3 etudiant

    @GetMapping("/getEtudiantFromClass/{idClasse}")
    public List<User> getEtudiantFromClass(@PathVariable("idClasse") String idClasse){
        return classeService.getEtudiantFromClass(idClasse);
    }
//hedhoum tjibli list mta3 el claase
    @GetMapping("/getProfessorFromClass/{idClasse}")
    public List<User> getProfessorFromClass(@PathVariable("idClasse") String idClasse){
        return classeService.getProfessorFromClass(idClasse);
    }

    @PostMapping("/affecterUserInClass/{idUser}/{idClasse}")
    public Classe affecterUserInClass(@PathVariable String idUser,@PathVariable  String idClasse) {
        return classeService.affecterUserInClass(idUser,idClasse);

    }

    @GetMapping("/getEtudiant")
    public List<User> getEtudiant(){
        return classeService.getEtudiant();
    }
    @GetMapping("/getEnsignat")
    public List<User> getEnseignat(){
        return classeService.getEnsignat();
    }





}
