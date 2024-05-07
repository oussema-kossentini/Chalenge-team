package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.repositories.EvaluationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EvaluationService implements IEvaluationService{
    EvaluationRepository evaluationRepository;
    @Override
    public Evaluation addEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public List<Evaluation> retrieveAllEvaluations() {
        return evaluationRepository.findAll();

    }

    @Override
    public void removeEvaluation(String id) {
        evaluationRepository.deleteById(id);

    }

    @Override
    public Evaluation modifyEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }
}
