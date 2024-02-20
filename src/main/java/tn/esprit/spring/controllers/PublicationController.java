package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.services.IPublicationService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/publications")
public class PublicationController {
    IPublicationService publicationService;
    @PostMapping("add/publication")
    public Publication addingPub(@RequestBody Publication publication){
        return publicationService.addPublication(publication);
    }
    @GetMapping("/retrieve-all-Publications")
    public List<Publication> getPublications() {
        List<Publication> listChambres = publicationService.retrieveAllPublications();
        return listChambres;
    }
    @DeleteMapping("/remove-publication/{publication-id}")
    public void removePublication(@PathVariable("publication-id") String chId) {
        publicationService.removePublication(chId);
    }
    // http://localhost:8080/tpfoyer/chambre/modify-chambre
    @PutMapping("/modify-publication/{id}")
    public Publication modifyPublication(@PathVariable String id,@RequestBody Publication c) {
        c.setIdPublication(id);
        return publicationService.addPublication(c);

    }
}



