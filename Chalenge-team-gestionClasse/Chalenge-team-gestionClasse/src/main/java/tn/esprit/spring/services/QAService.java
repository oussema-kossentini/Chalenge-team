package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.entities.QA;
import tn.esprit.spring.repositories.PublicationRepository;
import tn.esprit.spring.repositories.QaRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QAService implements IQAService{
    QaRepository qaRepository;

    @Override
    public QA addQA(QA qa) {
        return qaRepository.save(qa);
    }

    @Override
    public List<QA> retrieveAllQA() {
        return qaRepository.findAll();

    }

    @Override
    public void removeQa(String id) {
        qaRepository.deleteById(id);

    }

    @Override
    public QA modifyQa(QA bloc) {
        return qaRepository.save(bloc);
    }
}
