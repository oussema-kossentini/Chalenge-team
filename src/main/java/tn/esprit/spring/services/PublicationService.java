package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.PublicationRepository;
import tn.esprit.spring.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublicationService implements IPublicationService {
    PublicationRepository publicationRepository;
     UserRepository userRepository;

    // Méthode pour générer un token d'image
    private String generateImageToken() {
        // Générer un UUID (Universal Unique Identifier)
        UUID uuid = UUID.randomUUID();
        // Convertir l'UUID en une chaîne de caractères et la retourner
        return uuid.toString();
    }
    @Override
    // ... dans votre service
    public Publication addPublication(Publication publication) {

        // Logique pour générer et associer un token d'image à la publication
        String imageToken = generateImageToken(); // Méthode fictive pour générer le token
        publication.setImageToken(imageToken);
        // Enregistrez la publication dans la base de données
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

    @Override
    public Publication getPublicationById(String idPublication) {
        return publicationRepository.findById(idPublication).get();

    }

    @Override
    public List<Publication> searchPublicationsByTitle(String title) {
        List<Publication> publications = retrieveAllPublications();

        // Si un titre est fourni, filtrer les publications en fonction du titre
        if (title != null && !title.isEmpty()) {
            publications = publications.stream()
                    .filter(publication -> publication.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return publications;

    }

    @Override
    public void sharePublication(String publicationId) {
        Optional<Publication> optionalPublication = publicationRepository.findById(publicationId);
        if (optionalPublication.isPresent()) {
            Publication publication = optionalPublication.get();
            // Incrémenter le nombre de partages
            publication.setShareCount(publication.getShareCount() + 1);
            // Mettre à jour la publication dans la base de données
            publicationRepository.save(publication);
        } else {
        }
    }




}
