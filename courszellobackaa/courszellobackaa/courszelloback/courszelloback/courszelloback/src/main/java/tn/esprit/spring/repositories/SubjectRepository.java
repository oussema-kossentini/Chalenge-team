package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.Subject;

public interface SubjectRepository extends MongoRepository<Subject,String> {
}
