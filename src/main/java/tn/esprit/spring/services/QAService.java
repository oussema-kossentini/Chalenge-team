package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.entities.QA;
import tn.esprit.spring.repositories.EvaluationRepository;
import tn.esprit.spring.repositories.PublicationRepository;
import tn.esprit.spring.repositories.QaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QAService implements IQAService{
    QaRepository qaRepository;
    EvaluationRepository evaluationRepository;

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

    @Override
    public void assignQaToEvaluation(String qaId, String evaluationId) {
        QA qa = qaRepository.findById(qaId).orElseThrow(() -> new RuntimeException("Qa not found"));
        Evaluation evaluation = evaluationRepository
                .findById(evaluationId).orElseThrow(() -> new RuntimeException("evaluation not found"));
        if( evaluation.getQas() == null){
            evaluation.setQas(new ArrayList<>());
        }
        evaluation.getQas().add(qa);
        evaluationRepository.save(evaluation);

    }

    @Override
    public List<QA> getQaByEvaluationId(String evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new RuntimeException("could not found evaluation with id " + evaluationId));

        return evaluation.getQas();
    }

}
