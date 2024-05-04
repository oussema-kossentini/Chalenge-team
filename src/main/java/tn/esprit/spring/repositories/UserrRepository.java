package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Userr;

@Repository
public interface UserrRepository extends MongoRepository<Userr, String> {
}
