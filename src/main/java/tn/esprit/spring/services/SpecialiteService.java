package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.SpecialiteRepository;
import tn.esprit.spring.repositories.UserRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class SpecialiteService implements ISpecialiteService{
    @Autowired
    SpecialiteRepository specialiteRepository;
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    UserRepository userRepository;
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

    @Override
    public Specialite getSpecialiteById(String idSpecialite) {
        return specialiteRepository.findById(idSpecialite).get();
    }

    @Override
    public List<Classe> getSpecialiteById2(String idSpecialite) {
        List<Classe> classes = new ArrayList<>();
        Specialite specialite  = specialiteRepository.findById(idSpecialite).orElse(null);
        if(specialite.getClassesIds()!=null)
        for (String idClasse: specialite.getClassesIds()) {
            Classe classe = classeRepository.findById(idClasse).orElse(null);
            if(classe != null) {
                classes.add(classe);
            }
        }

        return classes;
    }


    //Statistique Etudiant par specialite
    @Override
    public Map<String, Integer> statEtudiantParSpecialite() {

        Integer nombreEtudiant=0;
        float pourcentage=0;

        // Get All student ROLE nombre totale Student
        for (User etudiant:userRepository.findAll()){
            if(etudiant.getRole().toString().equals("STUDENT"))
            {
                nombreEtudiant++;
            }
        }

        Map<String,Integer> MapEtudiantParSpecialite = new HashMap<>() ;

        for (Specialite specialite : specialiteRepository.findAll())
        {

            List<User> usersParSpecialite= new ArrayList<>();
            List<Classe> classes = new ArrayList<>();
            for (String Idclasse:specialite.getClassesIds()){
                Classe classe = classeRepository.findById(Idclasse).orElse(null);
                classes.add(classe);

            }

            if(classes!=null)
            for (Classe Classe:classes){


                if(Classe!=null)
                for (String idUser : Classe.getUsersIds()){
                    User etudiant = userRepository.findById(idUser).orElse(null);
                    if(etudiant.getRole().equals(Role.STUDENT)){

                        boolean test=false;
                        for (User user : usersParSpecialite){
                            if(user.getIdUser().toString().equals(etudiant.getIdUser())){
                                test=true;

                            }
                        }
                        if(test==false) {
                            usersParSpecialite.add(etudiant);
                        }
                    }

                }
            }


            MapEtudiantParSpecialite.put(specialite.getTitle(),usersParSpecialite.size());

        }



        for (Map.Entry<String, Integer> entry : MapEtudiantParSpecialite.entrySet()) {
            String key = entry.getKey();
            Integer nbrEPS = entry.getValue();
            pourcentage = (nbrEPS.floatValue()/ nombreEtudiant.floatValue()) * 100 ;
            System.out.println("Specialite:"+key+" Pourcentage : "+pourcentage+"%");

        }

        return MapEtudiantParSpecialite;
    }

    @Override
    public Map<String, Integer> statProfesseurParSpecialite() {
        Integer nombreProfesseur=0;
        float pourcentage=0;

        // Get All student ROLE nombre totale Student
        for (User professeur:userRepository.findAll()){
            if(professeur.getRole().toString().equals("PROFESSOR"))
            {
                nombreProfesseur++;
            }
        }

        Map<String,Integer> MapProfesseurParSpecialite = new HashMap<>() ;

        List<User> usersParALLSpecialite =new ArrayList<>();
        List<User> usersParALLSpecialite2 = new ArrayList<>();
        for (Specialite specialite : specialiteRepository.findAll())
        {

            List<User> usersParSpecialite= new ArrayList<>();
            List<Classe> classes = new ArrayList<>();
            for (String Idclasse:specialite.getClassesIds()){
                Classe classe = classeRepository.findById(Idclasse).orElse(null);
                classes.add(classe);


            }

            if(classes!=null)
                for (Classe Classe:classes){
                    if(Classe!=null)

                        for (String idUser : Classe.getUsersIds()){
                            User professeur = userRepository.findById(idUser).orElse(null);
                            if(professeur.getRole().equals(Role.PROFESSOR)){

                                boolean test=false;

                                for (User user : usersParSpecialite){
                                    if(user.getIdUser().toString().equals(professeur.getIdUser())){
                                        test=true; // kif yel9ah deja mawjoud Y7ot true donc mayzidouch

                                    }
                                }

                                if(test==false) { // kif teba false ma3neha mal9ahech donc yzidou
                                    usersParSpecialite.add(professeur);
                                    usersParALLSpecialite.add(professeur);
                                    usersParALLSpecialite2.add(professeur);
                                }


                            }

                        }
                }



            MapProfesseurParSpecialite.put(specialite.getTitle(),usersParSpecialite.size());

        }


        for (User professeurSpecialite1 : usersParALLSpecialite){
            int exist=0;

            for (User professeurSpecialite2 : usersParALLSpecialite2){
                if(professeurSpecialite2.getIdUser().toString().equals(professeurSpecialite1.getIdUser().toString())){
                        usersParALLSpecialite2.remove(professeurSpecialite2);
                    exist++;
                }
            }
            if(exist>1){
                exist--;
                nombreProfesseur=nombreProfesseur+exist;

            }
        }




        System.out.println(nombreProfesseur);


        for (Map.Entry<String, Integer> entry : MapProfesseurParSpecialite.entrySet()) {
            String key = entry.getKey();
            Integer nbrEPS = entry.getValue();
            pourcentage = (nbrEPS.floatValue()/ nombreProfesseur.floatValue()) * 100 ;
            System.out.println("Specialite:"+key+" Pourcentage : "+pourcentage+"%");

        }

        return MapProfesseurParSpecialite;
    }


    // @Override
   // public Specialite ajouterFoyerEtAffecterAUniversite(Classe classe, String idSpecialite) {
        // Sauvegarde de l'objet Classe
       // Classe savedClasse = classeRepository.save(classe);

        // Récupération de la spécialité par son ID
       // Specialite specialite = specialiteRepository.findById(idSpecialite)
               // .orElseThrow(() -> new RuntimeException("Specialite not found"));

        // Ajout de l'ID de la classe sauvegardée à la collection des IDs de Classe dans Specialite
      //  specialite.getClassesIds().add(savedClasse.getIdClasse());
      //  specialiteRepository.save(specialite);

        //return savedClasse;
   // }
}
