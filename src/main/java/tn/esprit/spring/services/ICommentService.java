package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Comment;

import java.util.List;

public interface ICommentService {
    Comment addComment(Comment comment);
    public List<Comment> retrieveAllComments();
    public void removeComment(String id);
    public Comment modifyComment(Comment bloc);
}
