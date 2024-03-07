package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.QA;

import java.util.List;

public interface QaRepository extends MongoRepository<QA,String> {

    List<QA> findAllByEvaluation(Evaluation evaluation);
}
