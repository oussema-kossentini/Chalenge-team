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
    public Map<String, Float> statEtudiantParSpecialite() {
/*
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

        Map<String,Float> etudiantparSpec = new HashMap<>();


        for (Map.Entry<String, Integer> entry : MapEtudiantParSpecialite.entrySet()) {
            String key = entry.getKey();
            Integer nbrEPS = entry.getValue();
            pourcentage = (nbrEPS.floatValue()/ nombreEtudiant.floatValue()) * 100 ;
            etudiantparSpec.put(key,pourcentage);
            System.out.println("Specialite:"+key+" Pourcentage : "+pourcentage+"%");

        }

        return etudiantparSpec;

 */
        Integer nombreEtudiant = 0;

        // Efficiently track if a user has already been considered
        Set<String> addedUserIds = new HashSet<>();

        // Count total number of students
        for (User etudiant : userRepository.findAll()) {
            if (etudiant != null && etudiant.getRole() != null && etudiant.getRole().toString().equals("STUDENT")) {
                nombreEtudiant++;
            }
        }

        Map<String, Integer> mapEtudiantParSpecialite = new HashMap<>();

        for (Specialite specialite : specialiteRepository.findAll()) {

            List<User> usersParSpecialite = new ArrayList<>();
            List<Classe> classes = new ArrayList<>();

            for (String Idclasse : specialite.getClassesIds()) {
                Classe classe = classeRepository.findById(Idclasse).orElse(null);
                if (classe != null) {
                    classes.add(classe);
                }
            }

            for (Classe classe : classes) {
                for (String idUser : classe.getUsersIds()) {
                    User etudiant = userRepository.findById(idUser).orElse(null);
                    if (etudiant != null && etudiant.getRole() == Role.STUDENT && !addedUserIds.contains(etudiant.getIdUser())) {
                        usersParSpecialite.add(etudiant);
                        addedUserIds.add(etudiant.getIdUser().toString());
                    }
                }
            }

            mapEtudiantParSpecialite.put(specialite.getTitle(), usersParSpecialite.size());
        }

        Map<String, Float> etudiantParSpec = new HashMap<>();

        for (Map.Entry<String, Integer> entry : mapEtudiantParSpecialite.entrySet()) {
            String key = entry.getKey();
            Integer nbrEPS = entry.getValue();
            float pourcentage = (nbrEPS.floatValue() / nombreEtudiant.floatValue()) * 100;
            etudiantParSpec.put(key, pourcentage);
            System.out.println("Specialite:" + key + " Pourcentage : " + pourcentage + "%");
        }

        return etudiantParSpec;
    }


    @Override
    public Map<String, Float> statProfesseurParSpecialite() {
        /*
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
                           // if(professeur.getRole().equals(Role.PROFESSOR)){
                            if (professeur.getRole() != null && professeur.getRole().equals(Role.PROFESSOR)){
                                boolean test=false;

                                for (User user : usersParSpecialite){
                                    if(user.getIdUser().toString().equals(professeur.getIdUser())){
                                        test=true; // kif yel9ah deja mawjoud Y7ot true donc mayzidouch

                                    }
                                }

                                if(test==false) { // kif teba false ma3neha mal9ahech donc yzidou
                                    usersParSpecialite.add(professeur);
                                    usersParALLSpecialite.add(professeur); // tlem les professeur eli fl specialites l kol
                                }


                            }

                        }
                }

            MapProfesseurParSpecialite.put(specialite.getTitle(),usersParSpecialite.size());

        }

// boucle fl all specialite hani fl war9a
        for (User professeurSpecialite1 : usersParALLSpecialite){
            int exist=0;
            boolean test = false;


            for(User userVerifexist : usersParALLSpecialite2){
                System.out.println(userVerifexist.getFirstName());
                if(userVerifexist.getIdUser().toString().equals(professeurSpecialite1.getIdUser().toString())){
                    test=true;
                }
            }

            if(test==false) {
                for (User professeurSpecialite2 : usersParALLSpecialite) {
                    if (professeurSpecialite2.getIdUser().toString().equals(professeurSpecialite1.getIdUser().toString())) {
                        usersParALLSpecialite2.add(professeurSpecialite2);
                        exist++;
                    }
                }
                if(exist>1){
                    exist--;
                    nombreProfesseur=nombreProfesseur+exist;

                }
            }

        }
        // eli hiya bch ta3tini porcentage bl fasel
        Map<String,Float> professeurparSpec = new HashMap<>();
//***********************************************************************************
        for (Map.Entry<String, Integer> entry : MapProfesseurParSpecialite.entrySet()) {
            String key = entry.getKey();
            Integer nbrEPS = entry.getValue();
            pourcentage = (nbrEPS.floatValue()/ nombreProfesseur.floatValue()) * 100 ;
            professeurparSpec.put(key,pourcentage);

            System.out.println("Specialite:"+key+" Pourcentage : "+pourcentage+"%");

        }

        return professeurparSpec;

         */
        Integer nombreProfesseur = 0;
        float pourcentage = 0;

        // Get All student ROLE nombre totale Student
        for (User professeur : userRepository.findAll()) {
            if (professeur != null && professeur.getRole() != null && professeur.getRole().equals(Role.PROFESSOR)) {
                nombreProfesseur++;
            }
        }

        Map<String, Integer> MapProfesseurParSpecialite = new HashMap<>();
        List<User> usersParALLSpecialite = new ArrayList<>();
        List<User> usersParALLSpecialite2 = new ArrayList<>();

        for (Specialite specialite : specialiteRepository.findAll()) {
            List<User> usersParSpecialite = new ArrayList<>();
            List<Classe> classes = new ArrayList<>();

            for (String Idclasse : specialite.getClassesIds()) {
                Classe classe = classeRepository.findById(Idclasse).orElse(null);
                if (classe != null) {
                    classes.add(classe);
                }
            }

            for (Classe Classe : classes) {
                if (Classe != null) {
                    for (String idUser : Classe.getUsersIds()) {
                        User professeur = userRepository.findById(idUser).orElse(null);
                        if (professeur != null && professeur.getRole() != null && professeur.getRole().equals(Role.PROFESSOR)) {
                            boolean test = false;
                            for (User user : usersParSpecialite) {
                                if (user != null && user.getIdUser().toString().equals(professeur.getIdUser())) {
                                    test = true;
                                    break;
                                }
                            }

                            if (!test) {
                                usersParSpecialite.add(professeur);
                                usersParALLSpecialite.add(professeur);
                            }
                        }
                    }
                }
            }

            MapProfesseurParSpecialite.put(specialite.getTitle(), usersParSpecialite.size());
        }

        for (User professeurSpecialite1 : usersParALLSpecialite) {
            int exist = 0;
            boolean test = false;

            for (User userVerifexist : usersParALLSpecialite2) {
                if (userVerifexist != null && professeurSpecialite1 != null && userVerifexist.getIdUser().toString().equals(professeurSpecialite1.getIdUser().toString())) {
                    test = true;
                    break;
                }
            }

            if (!test) {
                for (User professeurSpecialite2 : usersParALLSpecialite) {
                    if (professeurSpecialite2 != null && professeurSpecialite1 != null && professeurSpecialite2.getIdUser().toString().equals(professeurSpecialite1.getIdUser().toString())) {
                        usersParALLSpecialite2.add(professeurSpecialite2);
                        exist++;
                    }
                }
                if (exist > 1) {
                    exist--;
                    nombreProfesseur += exist;
                }
            }
        }

        Map<String, Float> professeurparSpec = new HashMap<>();

        for (Map.Entry<String, Integer> entry : MapProfesseurParSpecialite.entrySet()) {
            String key = entry.getKey();
            Integer nbrEPS = entry.getValue();
            pourcentage = (nbrEPS.floatValue() / nombreProfesseur.floatValue()) * 100;
            professeurparSpec.put(key, pourcentage);

            System.out.println("Specialite:" + key + " Pourcentage : " + pourcentage + "%");
        }

        return professeurparSpec;
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


    public List<String> getAllTitles() {
        return specialiteRepository.findAll().stream().map(specialite -> specialite.getTitle()).toList();
    }
}
