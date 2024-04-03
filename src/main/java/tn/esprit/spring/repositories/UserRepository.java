package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    @Query("{'email' : ?0}")
    Optional<User> findByEmail(String email);

}