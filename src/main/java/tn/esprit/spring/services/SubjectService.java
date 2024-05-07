package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.repositories.SubjectRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectService implements ISubjectService {
SubjectRepository subjectRepository;
    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> retrieveAllSubjects() {
        return subjectRepository.findAll();

    }

    @Override
    public void removeSubject(String id) {
        subjectRepository.deleteById(id);

    }

    @Override
    public Subject modifySubject(Subject bloc) {
        return subjectRepository.save(bloc);
    }
}
