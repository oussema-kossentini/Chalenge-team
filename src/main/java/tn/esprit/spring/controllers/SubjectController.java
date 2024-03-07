package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Session;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.services.ISessionService;
import tn.esprit.spring.services.ISubjectService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/subject")
public class SubjectController {
    ISubjectService subjectService;
    @PostMapping("add/subject")
    public Subject addSubject(@RequestBody Subject us){
        return subjectService.addSubject(us);
    }
    @GetMapping("/retrieve-all-subjects")
    public List<Subject> getSubject() {
        List<Subject> listSubject = subjectService.retrieveAllSubjects();
        return listSubject;
    }
    @DeleteMapping("/remove-subject/{subject-id}")
    public void removeSubject(@PathVariable("subject-id") String chId) {
        subjectService.removeSubject(chId);
    }
    @PutMapping("/modify-subject/{id}")
    public Subject modifySubject(@PathVariable String id,@RequestBody Subject c) {
        c.setIdSubject(id);
        return subjectService.addSubject(c);

    }
}
