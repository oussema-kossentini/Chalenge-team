package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.Session;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.IScheduleService;
import tn.esprit.spring.services.ISessionService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/session")
@CrossOrigin(origins = "http://localhost:4200")
public class SessionController {
    ISessionService sessionService;
UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:4200")
  /*  @PostMapping("add/session")
    public ResponseEntity<?> addSession(@RequestBody Session session, @RequestParam("idScheduel") String idScheduel, @RequestParam("idSubject") String idSubject) {
        try {
            // La méthode addSession retourne maintenant un boolean
            boolean sessionAdded = sessionService.addSession(session, idScheduel, idSubject);

            if(sessionAdded) {
                // Si la session est ajoutée avec succès, retourner la session et un statut 200 OK
                return ResponseEntity.ok(session);
            } else {
                // Si la session n'est pas ajoutée à cause d'un chevauchement, retourner une réponse appropriée
                return ResponseEntity.badRequest().body("Session could not be added due to overlap");
            }
        } catch (RuntimeException e) {
            // Gestion des exceptions
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }*/
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @PostMapping("add/session")
    public ResponseEntity<?> addSession(Authentication authentication, @RequestBody Session session, @RequestParam("idScheduel") String idScheduel, @RequestParam("idSubject") String idSubject) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        try {

            // Assuming the method signature of addSession has been updated to accept idSubject as well
            Session addedSession = sessionService.addSession(session, idScheduel, idSubject);
            return ResponseEntity.ok(addedSession);
        } catch (RuntimeException e) {
            // Consider logging the exception here for debugging purposes
            // e.g., logger.error("Error adding session", e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("/retrieve-all-Session")
    public List<Session> getSessions(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Session> listSessions = sessionService.retrieveAllSessions();
        return listSessions;
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @DeleteMapping("/remove-session/{session-id}")
    public void removeSession(Authentication authentication,@PathVariable("session-id") String chId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        sessionService.removeSession(chId);
    }

   /* @PutMapping("/modify-session/{id}")
    public Session modifySession(@PathVariable String id, @RequestBody Session c) {
        c.setIdSession(id);
        return sessionService.addSession(c);

    }*/
   @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @PostMapping("/add-S-SH/{idScheduel}")
    public ResponseEntity<Session> addScheduelTosss(Authentication authentication,
                                                    @RequestBody Session session, @PathVariable String idScheduel) {
       String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
       User user = userRepository.findByEmail(userEmail)
               .orElseThrow(() -> new IllegalStateException("User not found"));
        try {

            Session addedSession = sessionService.addSessionScheduel(session, idScheduel);
            return ResponseEntity.ok(addedSession);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}