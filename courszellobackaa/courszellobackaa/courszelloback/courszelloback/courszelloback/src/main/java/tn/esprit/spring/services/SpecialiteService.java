package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Specialite;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.repositories.SpecialiteRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class SpecialiteService implements ISpecialiteService{
    SpecialiteRepository specialiteRepository;
    @Override
    public Specialite addSpecialite(Specialite specialite) {
        return specialiteRepository.save(specialite);

    }

    @Override
    public List<Specialite> retrieveAllSpecialities() {
        return specialiteRepository.findAll();
    }

    @Override
    public void removeSpecialite(String id) {
        specialiteRepository.deleteById(id);
    }

    @Override
    public Specialite modifySpecialite(Specialite bloc) {
        return specialiteRepository.save(bloc);

    }
}
