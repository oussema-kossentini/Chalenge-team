package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.EvaluationAttempts;

import java.util.List;

@Repository
public interface EvaluationAttemptsRepository extends MongoRepository<EvaluationAttempts,String> {
    List<EvaluationAttempts> findAllByUserIdIdUser(String userId);
}
