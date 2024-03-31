package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.spring.entities.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
