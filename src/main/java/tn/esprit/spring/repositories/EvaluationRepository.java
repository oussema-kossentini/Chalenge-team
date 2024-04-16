package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.spring.entities.Categorie;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.User;

import java.util.List;

public interface EvaluationRepository extends MongoRepository<Evaluation,String> {
    @Query("{ 'classe.users': ?0, 'categorie': ?1 }")
    List<Evaluation> findByUserIdAndCategorie(String userId, String categorie);


}
