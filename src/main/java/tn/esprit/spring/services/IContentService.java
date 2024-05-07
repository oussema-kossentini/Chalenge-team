package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Content;

import java.util.List;

public interface IContentService{
    Content addContent(Content content);
    public List<Content> retrieveAllContents();
    public void removeContent(String id);
    public Content modifyContent(Content bloc);
}
