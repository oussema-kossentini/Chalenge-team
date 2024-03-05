package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Publication;
@Repository
public interface PublicationRepository extends MongoRepository<Publication, String> {
}
