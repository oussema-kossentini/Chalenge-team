package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
public class SessionController {
    ISessionService sessionService;
    @PostMapping("add/session")
    public Session addSession(@RequestBody Session us){
        return sessionService.addSession(us);
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

    @PutMapping("/modify-session/{id}")
    public Session modifySession(@PathVariable String id,@RequestBody Session c) {
        c.setIdSession(id);
        return sessionService.addSession(c);

    }
}
