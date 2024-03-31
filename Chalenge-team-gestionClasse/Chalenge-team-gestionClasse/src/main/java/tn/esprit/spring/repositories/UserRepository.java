package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
}
