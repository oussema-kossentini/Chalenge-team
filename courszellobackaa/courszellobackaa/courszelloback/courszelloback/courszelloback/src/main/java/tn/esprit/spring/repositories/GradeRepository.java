package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.Grade;

public interface GradeRepository extends MongoRepository<Grade,String> {
}
