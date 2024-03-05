package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Session;
import tn.esprit.spring.entities.Specialite;
import tn.esprit.spring.repositories.SpecialiteRepository;
import tn.esprit.spring.services.ISessionService;
import tn.esprit.spring.services.ISpecialiteService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/specilite")
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

    @PutMapping("/modify-specialite/{id}")
    public Specialite modifySpecialite(@PathVariable String id,@RequestBody Specialite c) {
        c.setIdSpecialite(id);
        return specialiteService.addSpecialite(c);
    }
    }
