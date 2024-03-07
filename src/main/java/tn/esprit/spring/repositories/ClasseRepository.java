package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Classe;

@Repository
public interface ClasseRepository extends MongoRepository<Classe,String> {
}
