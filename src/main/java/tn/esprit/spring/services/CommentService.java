package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Config.CommentNotFoundException;
import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.repositories.CommentRepository;
import tn.esprit.spring.repositories.ContentRepository;
import tn.esprit.spring.repositories.PublicationRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService implements ICommentService {
    CommentRepository commentRepository;
    PublicationRepository publicationRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
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

    @Override
    public Comment addCommentToPublication(String publicationId, Comment comment) {
        Optional<Publication> optionalPublication = publicationRepository.findById(publicationId);
        if (optionalPublication.isPresent()) {
            Publication publication = optionalPublication.get();

            if (publication.getComments() == null) {
                publication.setComments(new HashSet<>());
            }

            // Associer la publication au commentaire
            comment.setPublication(publication);

            // Enregistrer le commentaire
            Comment savedComment = commentRepository.save(comment);

            // Ajouter le commentaire à la liste de commentaires de la publication
            publication.getComments().add(savedComment);
            publicationRepository.save(publication);

            return savedComment;
        } else {
            throw new RuntimeException("Publication not found with id: " + publicationId);
        }
    }

    @Override
    public List<Comment> getCommentsForPublication(String publicationId) {
        return commentRepository.findByPublication_IdPublication(publicationId);

    }


}