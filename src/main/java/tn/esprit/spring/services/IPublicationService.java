package tn.esprit.spring.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.*;

import java.util.List;

public interface IPublicationService {
    Publication addPublication(Publication publicatione);
    public List<Publication> retrieveAllPublications();
    public void removePublication(String id);
    public Publication modifyPublication(Publication publication);
    public Publication getPublicationById(String idPublication);
    public List<Publication> searchPublicationsByTitle(String title);
    public void sharePublication(String publicationId) ;
}
