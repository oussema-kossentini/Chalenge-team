package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.repositories.CommentRepository;
import tn.esprit.spring.repositories.ContentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService implements ICommentService{
     CommentRepository commentRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment) ;
    }

    @Override
    public List<Comment> retrieveAllComments() {
        return commentRepository.findAll();

    }

    @Override
    public void removeComment(String id) {
        commentRepository.deleteById(id);

    }

    @Override
    public Comment modifyComment(Comment bloc) {
        return commentRepository.save(bloc);
    }
}
