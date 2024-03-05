package tn.esprit.spring.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.ContentFormat;
import tn.esprit.spring.entities.Publication;

import java.util.List;

public interface IPublicationService {
    Publication addPublication(Publication publicatione);
    public List<Publication> retrieveAllPublications();
    public void removePublication(String id);
    public Publication modifyPublication(Publication publication);
    public Publication getPublicationById(String idPublication);
}
