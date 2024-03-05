package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.repositories.PublicationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PublicationService implements IPublicationService {
    PublicationRepository publicationRepository;
    @Override
    public Publication addPublication(Publication publication) {
            return publicationRepository.save(publication);

        }

    @Override
    public List<Publication> retrieveAllPublications() {
        return publicationRepository.findAll();

    }

    @Override
    public void removePublication(String id) {
        publicationRepository.deleteById(id);

    }

    @Override
    public Publication modifyPublication(Publication publication) {
        return publicationRepository.save(publication);
    }

}
