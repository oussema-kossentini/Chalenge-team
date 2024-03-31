package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Content;
import tn.esprit.spring.services.IClasseService;
import tn.esprit.spring.services.IContentService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/Content")
public class ContentController {
    IContentService contentService;
    @PostMapping("add/Content")
    public Content addContent(@RequestBody Content us){

        return contentService.addContent(us);
    }
    @GetMapping("/retrieve-all-contents")
    public List<Content> getContents() {
        List<Content> listContents = contentService.retrieveAllContents();
        return listContents;
    }
    @DeleteMapping("/remove-contents/{content-id}")
    public void removeChambre(@PathVariable("chambre-id") String chId) {
        contentService.removeContent(chId);
    }
    @PutMapping("/modify-content/{id}")
    public Content modifyChambre(@PathVariable String id,@RequestBody Content c) {
        c.setIdContent(id);
        return contentService.addContent(c);

    }
}
