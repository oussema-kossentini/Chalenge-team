package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.SpecialiteRepository;
import tn.esprit.spring.services.ISessionService;
import tn.esprit.spring.services.ISpecialiteService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/specilite")
@CrossOrigin(origins = "http://localhost:4200/**")
public class SpecialiteController {
@Autowired
    ISpecialiteService specialiteService;
    @PostMapping("add/specialite")
    public Specialite addSpecialite(@RequestBody Specialite sp){
        return specialiteService.addSpecialite(sp);
    }
    @GetMapping("/retrieve-all-specialities")
    public List<Specialite> getSpecialities() {
        List<Specialite> listSpecialities = specialiteService.retrieveAllSpecialities();
        return listSpecialities;
    }
    @DeleteMapping("/remove-specialite/{specialite-id}")
    public void removeSession(@PathVariable("specialite-id") String chId) {

        specialiteService.removeSpecialite(chId);
    }


    //@PutMapping("/modify-specialite/{id}")
   // public Specialite modifySpecialite(@PathVariable String id,@RequestBody Specialite c) {
      //  c.setIdSpecialite(id);
       // return specialiteService.addSpecialite(c);
   // }
    @PutMapping("/modify-specialite")
    public ResponseEntity<Specialite> modifyUser(@RequestBody Specialite classe){

        Specialite updatedUser = specialiteService.modifySpecialite(classe); // Assuming there's a method in userService to modify the user
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public Specialite gettingSpecialite(@RequestParam("specialite-id") String idSpecilite){
        return specialiteService.getSpecialiteById(idSpecilite);
    }

    //deatil mta3 el claase fi wost specialite
    @GetMapping("/getclasseBySpecialite/{id}")
    public List<Classe> gettingSpecialite2(@PathVariable("id") String idSpecilite){
        return specialiteService.getSpecialiteById2(idSpecilite);
    }

    @GetMapping("/statEtudiantParSpecialite")
    public Map<String, Float> statEtudiantParSpecialite(){
        return specialiteService.statEtudiantParSpecialite();
    }

    @GetMapping("/statProfesseurParSpecialite")
    public Map<String, Float> statProfesseurParSpecialite(){
        return specialiteService.statProfesseurParSpecialite();
    }

    @GetMapping("/titles")
    public List<String> getAllTitles() {
        return specialiteService.getAllTitles();
    }
    }
