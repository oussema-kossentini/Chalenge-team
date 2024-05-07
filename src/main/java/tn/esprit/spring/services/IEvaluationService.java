package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Evaluation;

import java.util.List;

public interface IEvaluationService {
    Evaluation addEvaluation(Evaluation evaluation);
    public List<Evaluation> retrieveAllEvaluations();
    public void removeEvaluation(String id);
    public Evaluation modifyEvaluation(Evaluation evaluation);
}
