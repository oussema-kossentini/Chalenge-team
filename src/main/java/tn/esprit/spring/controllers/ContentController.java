package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Content;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.IClasseService;
import tn.esprit.spring.services.IContentService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/Content")
public class ContentController {
    IContentService contentService;
    UserRepository userRepository;
    @PreAuthorize("hasAuthority('ADMINISTRATOR')  || hasAuthority('TEACHER')  || hasAuthority('PROFESSOR')")

    @PostMapping("add/Content")
    public Content addContent(Authentication authentication,@RequestBody Content us){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return contentService.addContent(us);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")

    @GetMapping("/retrieve-all-contents")
    public List<Content> getContents(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Content> listContents = contentService.retrieveAllContents();
        return listContents;
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || hasAuthority('TEACHER')  || hasAuthority('PROFESSOR')")

    @DeleteMapping("/remove-contents/{content-id}")
    public void removeChambre(Authentication authentication,@PathVariable("content-id") String chId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        contentService.removeContent(chId);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || hasAuthority('TEACHER') || hasAuthority('PROFESSOR')")

    @PutMapping("/modify-content/{id}")
    public Content modifyChambre(Authentication authentication,@PathVariable String id,@RequestBody Content c) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        c.setIdContent(id);
        return contentService.addContent(c);

    }
}
