package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.Session;
@Repository
public interface SessionRepository extends MongoRepository<Session,String> {
}
