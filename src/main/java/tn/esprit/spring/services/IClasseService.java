package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;

import java.util.List;

public interface IClasseService {
    Classe addClasse(Classe classe);
    public List<Classe> retrieveAllClasses();
    public void removeClasse(String id);
    public Classe modifyClasse(Classe bloc);
}
