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
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.ICourseService;
import tn.esprit.spring.services.IScheduleService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:4200/**")
@RequestMapping("api/schedule")
public class ScheduleController {
    IScheduleService scheduleService;
    UserRepository userRepository;
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")

    @PostMapping("add/schedule")
    public Scheduel addschedule(Authentication authentication, @RequestBody Scheduel us){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return scheduleService.addSchedule(us);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("/retrieve-all-scheduels")
    public List<Scheduel> getChambres(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Scheduel> listChambres = scheduleService.retrieveAllScheduels();
        return listChambres;
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @DeleteMapping("/remove-scheduel/{scheduel-id}")
    public void removeChambre(Authentication authentication, @PathVariable("scheduel-id") String chId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        scheduleService.removeScheduel(chId);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @PutMapping("/modify-scheduel/{id}")
    public Scheduel modifyScheduel(Authentication authentication,@PathVariable String id,@RequestBody Scheduel c) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        c.setIdScheduel(id);
        return scheduleService.addSchedule(c);

    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("/{id}")
    public Scheduel getScheduelById(Authentication authentication, @PathVariable String id) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return scheduleService.retrieveScheduelById(id);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @PostMapping("/add-S-C/{idClasse}")
    public ResponseEntity<Scheduel> addScheduelToClasseP(Authentication authentication, @RequestBody Scheduel scheduel, @PathVariable String idClasse) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        try {
            Scheduel addedScheduel = scheduleService.addScheduelToClasseP(scheduel, idClasse);
            return ResponseEntity.ok(addedScheduel);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ")
    @PostMapping("/add-to-classe/{idClasse}")
    public ResponseEntity<Scheduel> addScheduelToClasse(Authentication authentication, @RequestBody Scheduel scheduel, @PathVariable String idClasse) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        try {
            Scheduel addedScheduel = scheduleService.addScheduelToClasse(scheduel, idClasse);
            return ResponseEntity.ok(addedScheduel);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}