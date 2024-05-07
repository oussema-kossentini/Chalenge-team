package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.Grade;
import tn.esprit.spring.repositories.GradeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GradeService implements IGradeService{
    GradeRepository gradeRepository;
    @Override
    public Grade addGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public List<Grade> retrieveAllGrades() {
        return gradeRepository.findAll();

    }

    @Override
    public void removeGrade(String id) {
        gradeRepository.deleteById(id);

    }

    @Override
    public Grade modifyGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

}
