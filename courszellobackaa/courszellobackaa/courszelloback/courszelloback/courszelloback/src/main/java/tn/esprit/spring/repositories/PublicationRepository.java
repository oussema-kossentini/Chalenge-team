package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.spring.entities.Publication;

public interface PublicationRepository extends MongoRepository<Publication, String> {
}
