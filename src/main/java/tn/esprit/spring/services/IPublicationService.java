package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Publication;

import java.util.List;

public interface IPublicationService {
    Publication addPublication(Publication publication);
    public List<Publication> retrieveAllPublications();
    public void removePublication(String id);
    public Publication modifyPublication(Publication publication);
}
