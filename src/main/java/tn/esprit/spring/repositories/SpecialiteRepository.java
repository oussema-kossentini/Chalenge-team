package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Specialite;
@Repository
public interface SpecialiteRepository extends MongoRepository<Specialite, String> {
}