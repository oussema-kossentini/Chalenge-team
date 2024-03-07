package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Grade;

import java.util.List;

public interface IGradeService {
    Grade addGrade(Grade grade);
    public List<Grade> retrieveAllGrades();
    public void removeGrade(String id);
    public Grade modifyGrade(Grade grade);
}
