package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.entities.Reaction;
import tn.esprit.spring.entities.User;

@Repository

public interface ReactionRepository extends MongoRepository<Reaction, String> {
    boolean existsByPublicationAndUser(Publication publication, User user);
    Reaction findByPublicationAndUser(Publication publication, User user);
}
