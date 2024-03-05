package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Comment;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByPublication_IdPublication(String publicationId);
    Optional<Comment> findCommentByIdCommentAndPublication_IdPublication(String publicationId, String commentId);

}
