package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Session;
import tn.esprit.spring.entities.Subject;

import java.util.List;

public interface ISubjectService {
    Subject addSubject(Subject subject);
    public List<Subject> retrieveAllSubjects();
    public void removeSubject(String id);
    public Subject modifySubject(Subject bloc);

}
