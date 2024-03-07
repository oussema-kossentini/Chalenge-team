package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.User;

import java.util.List;

public interface IEvaluationService {
    Evaluation addEvaluation(Evaluation evaluation);
     List<Evaluation> retrieveAllEvaluations();
     void removeEvaluation(String id);
     Evaluation modifyEvaluation(Evaluation evaluation);
    User assignEvaluationToUser(String userId, String evaluationId);

    Evaluation getEvaluationById(String evaluationId);

    Evaluation assignEvaluationToClasse(String classeId, String evaluationId);

}
