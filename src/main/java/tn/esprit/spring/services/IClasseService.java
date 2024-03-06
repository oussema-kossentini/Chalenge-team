package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.User;

import java.util.List;
import java.util.Optional;

public interface IClasseService {
    Classe addClasse(Classe classe);
    public List<Classe> retrieveAllClasses();
    public void removeClasse(String id);
    public Classe modifyClasse(Classe bloc);
    public Classe getClasseById(String idClasse);
    Classe ajouterFoyerEtAffecterAUniversite (Classe classe, String idSpecialite) ;
    List<Classe> retrievePostsByidUser( String idSpecialite );
    //public void removeUserFromAllClasses(String idUser);

    List<User> getEtudiantFromClass(String idClasse);
    List<User> getProfessorFromClass(String idClasse);

    Classe affecterUserInClass(String idUser,String idClasse);

    List<User> getEtudiant();
     List<User> getEnsignat();

    public void deleteClasseAndSpecialiteAssociation(String idClasse);



}
