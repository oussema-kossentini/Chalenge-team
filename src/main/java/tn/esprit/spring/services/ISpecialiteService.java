package tn.esprit.spring.services;

import tn.esprit.spring.entities.Specialite;
import tn.esprit.spring.entities.Subject;

import java.util.List;

public interface ISpecialiteService {
    Specialite addSpecialite(Specialite specialite);
    public List<Specialite> retrieveAllSpecialities();
    public void removeSpecialite(String id);
    public Specialite modifySpecialite(Specialite bloc);
}
