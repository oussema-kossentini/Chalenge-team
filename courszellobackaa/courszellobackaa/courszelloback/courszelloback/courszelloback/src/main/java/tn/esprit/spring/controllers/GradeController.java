package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Grade;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.services.IGradeService;
import tn.esprit.spring.services.IPublicationService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/grades")
public class GradeController {
    @Autowired
    IGradeService gradeService;
    @PostMapping("add/grade")
    public Grade addingGrade(@RequestBody Grade grade){
        return gradeService.addGrade(grade);
    }
    @GetMapping("/retrieve-all-grades")
    public List<Grade> getChambres() {
        List<Grade> listGrades = gradeService.retrieveAllGrades();
        return listGrades;
    }
    @DeleteMapping("/remove-grade/{grade-id}")
    public void removeGrades(@PathVariable("grade-id") String chId) {
        gradeService.removeGrade(chId);
    }
    @PutMapping("/modify-grade/{id}")
    public Grade modifyGrade(@PathVariable String id,@RequestBody Grade c) {
        c.setIdGrade(id);
        return gradeService.addGrade(c);

    }
}
