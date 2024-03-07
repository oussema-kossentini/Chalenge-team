package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Content;
import tn.esprit.spring.repositories.ContentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ContentService implements IContentService {
    ContentRepository contentRepository;

    @Override
    public Content addContent(Content content) {

        return contentRepository.save(content);
    }

    @Override
    public List<Content> retrieveAllContents() {
        return contentRepository.findAll();
    }

    @Override
    public void removeContent(String id) {
        contentRepository.deleteById(id);

    }

    @Override
    public Content modifyContent(Content bloc) {
        return contentRepository.save(bloc);

    }


}
