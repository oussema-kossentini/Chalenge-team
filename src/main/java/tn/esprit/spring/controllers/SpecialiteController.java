package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.dto.SpecialiteDto;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.SpecialiteRepository;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.ISessionService;
import tn.esprit.spring.services.ISpecialiteService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/specilite")
@CrossOrigin(origins = "http://localhost:4200/**")

public class SpecialiteController {
@Autowired
    ISpecialiteService specialiteService;
    UserRepository userRepository;
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @PostMapping("add/specialite")
    public Specialite addSpecialite(Authentication authentication, @RequestBody Specialite sp){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return specialiteService.addSpecialite(sp);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("/retrieve-all-specialities")
    public List<Specialite> getSpecialities(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Specialite> listSpecialities = specialiteService.retrieveAllSpecialities();
        return listSpecialities;
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @DeleteMapping("/remove-specialite/{specialite-id}")
    public void removeSession(Authentication authentication,@PathVariable("specialite-id") String chId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        specialiteService.removeSpecialite(chId);
    }


    //@PutMapping("/modify-specialite/{id}")
   // public Specialite modifySpecialite(@PathVariable String id,@RequestBody Specialite c) {
      //  c.setIdSpecialite(id);
       // return specialiteService.addSpecialite(c);
   // }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @PutMapping("/modify-specialite")
    public ResponseEntity<Specialite> modifyUser(Authentication authentication,@RequestBody Specialite classe){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Specialite updatedUser = specialiteService.modifySpecialite(classe); // Assuming there's a method in userService to modify the user
        return ResponseEntity.ok(updatedUser);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @GetMapping("/{id}")
    public Specialite gettingSpecialite(Authentication authentication,@RequestParam("specialite-id") String idSpecilite){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return specialiteService.getSpecialiteById(idSpecilite);
    }

    //deatil mta3 el claase fi wost specialite
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @GetMapping("/getclasseBySpecialite/{id}")
    public List<Classe> gettingSpecialite2(Authentication authentication,@PathVariable("id") String idSpecilite){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return specialiteService.getSpecialiteById2(idSpecilite);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @GetMapping("/statEtudiantParSpecialite")
    public Map<String, Float> statEtudiantParSpecialite(Authentication authentication){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return specialiteService.statEtudiantParSpecialite();
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @GetMapping("/statProfesseurParSpecialite")
    public Map<String, Float> statProfesseurParSpecialite(Authentication authentication){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return specialiteService.statProfesseurParSpecialite();
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @GetMapping("/titles")
    public List<String> getAllTitles(Authentication authentication) {

        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return specialiteService.getAllTitles();
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("/getNavUser")
    public String getNavUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new IllegalStateException("User not authenticated");
        }

        // Assuming that getUserNav() has been updated to use the authenticated user
        // and no longer requires an ID as an argument
        return specialiteService.getUserNav();
    }
   /*
    public String getNavUser(Authentication authentication,@PathVariable String idUser){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return  specialiteService.getUserNav(idUser);
    }
    */

    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("/getSpecialiteAndClasseFromProfesseur/{idUser}")
    public List<SpecialiteDto> getSpecialiteAndClasseFromProfesseur(Authentication authentication,@PathVariable String idUser) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return specialiteService.getSpecialiteAndClasseFromProfesseur(idUser);
    }

    }
