package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.QA;

public interface QaRepository extends MongoRepository<QA,String> {
}
