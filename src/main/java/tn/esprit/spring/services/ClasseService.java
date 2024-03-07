package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.repositories.ClasseRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ClasseService implements IClasseService{
    ClasseRepository classeRepository;

    @Override
    public Classe addClasse(Classe classe) {
        return classeRepository.save(classe);
    }
    @Override
    public List<Classe> retrieveAllClasses() {
        return classeRepository.findAll();
    }


    @Override
    public Classe modifyClasse(Classe bloc) {

        return classeRepository.save(bloc);
    }
    @Override
    public void removeClasse(String id) {
        classeRepository.deleteById(id);

    }
}
