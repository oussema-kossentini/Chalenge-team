package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.Session;
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


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("add/session")
    public ResponseEntity<?> addSession(@RequestBody Session session, @RequestParam("idScheduel") String idScheduel, @RequestParam("idSubject") String idSubject) {
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

    @GetMapping("/retrieve-all-Session")
    public List<Session> getSessions() {
        List<Session> listSessions = sessionService.retrieveAllSessions();
        return listSessions;
    }

    @DeleteMapping("/remove-session/{session-id}")
    public void removeSession(@PathVariable("session-id") String chId) {
        sessionService.removeSession(chId);
    }

   /* @PutMapping("/modify-session/{id}")
    public Session modifySession(@PathVariable String id, @RequestBody Session c) {
        c.setIdSession(id);
        return sessionService.addSession(c);

    }*/

    @PostMapping("/add-S-SH/{idScheduel}")
    public ResponseEntity<Session> addScheduelTosss(@RequestBody Session session, @PathVariable String idScheduel) {
        try {

            Session addedSession = sessionService.addSessionScheduel(session, idScheduel);
            return ResponseEntity.ok(addedSession);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
