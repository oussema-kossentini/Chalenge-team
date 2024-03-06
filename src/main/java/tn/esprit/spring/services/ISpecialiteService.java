package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Specialite;
import tn.esprit.spring.entities.Subject;

import java.util.List;
import java.util.Map;

public interface ISpecialiteService {
    Specialite addSpecialite(Specialite specialite);
    public List<Specialite> retrieveAllSpecialities();
    public void removeSpecialite(String id);
    public Specialite modifySpecialite(Specialite bloc);
    public Specialite getSpecialiteById(String idSpecialite);
    public List<Classe> getSpecialiteById2(String idSpecialite);
    public Map<String, Float> statEtudiantParSpecialite();
    public Map<String, Float> statProfesseurParSpecialite();
    public List<String> getAllTitles();


}
