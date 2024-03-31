package tn.esprit.spring.services;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.Config.TwilioConfig;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.SpecialiteRepository;
import tn.esprit.spring.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClasseService implements IClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SpecialiteRepository specialiteRepository;


    @Autowired
    TwilioConfig twilioConfig;

    @Override
    public Classe addClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    public List<Classe> retrieveAllClasses() {

        List<Classe> classes = classeRepository.findAll();
        // log.info("Retrieving classes: {}", classes);
        return classes;
    }


    @Override
    public Classe modifyClasse(Classe bloc) {

        return classeRepository.save(bloc);
    }

    @Override
    public Classe getClasseById(String idClasse) {
        return classeRepository.findById(idClasse).get();
    }

    @Override
    public Classe ajouterFoyerEtAffecterAUniversite(Classe classe, String idSpecialite) {
        // Sauvegarde de l'objet Classe
        Classe savedClasse = classeRepository.save(classe);
        System.out.println("Classe sauvegardée : " + savedClasse);

        // Récupération de la spécialité par son ID
        Specialite specialite = specialiteRepository.findById(idSpecialite)
                .orElseThrow(() -> new RuntimeException("Specialite not found with id: " + idSpecialite));

        // Ajoutez l'identifiant de la classe à l'entité Specialite
        specialite.getClassesIds().add(savedClasse.getIdClasse());
        specialiteRepository.save(specialite); // Sauvegarde de l'entité Specialite mise à jour

        // Mettez à jour la référence de la spécialité dans l'objet Classe
        savedClasse.setSpecialite(specialite);

        // Sauvegarde de l'objet Classe mis à jour avec la référence à la spécialité
        savedClasse = classeRepository.save(savedClasse);
        System.out.println("Classe mise à jour avec la spécialité : " + savedClasse);

        return savedClasse;
    }


    @Override
    public void deleteClasseAndSpecialiteAssociation(String idClasse) {
        // Retrieve the class by its ID
        Classe classe = classeRepository.findById(idClasse)
                .orElseThrow(() -> new RuntimeException("Classe not found with id: " + idClasse));

        // Retrieve the associated speciality
        Specialite specialite = classe.getSpecialite();

        // Remove the class ID from the associated speciality
        specialite.getClassesIds().remove(idClasse);

        // Save the updated speciality
        specialiteRepository.save(specialite);

        // Delete the class
        classeRepository.delete(classe);
    }


    @Override
    public List<Classe> retrievePostsByidUser(String idSpecialite) {
        List<Classe> posts = classeRepository.findBySpecialite(idSpecialite);

        if (posts != null && !posts.isEmpty()) {
            for (Classe post : posts) {
                System.out.println(post); // Affichage de chaque élément de la liste
            }
            return posts;
        } else {
            // Si aucun poste n'est trouvé, vous pouvez retourner une liste vide ou gérer cela selon vos besoins.
            return Collections.emptyList();
        }
    }


    @Override
    public void removeClasse(String id) {
        classeRepository.deleteById(id);

    }


    @Override
    public List<User> getEtudiantFromClass(String idClasse) {
        Classe classe = classeRepository.findById(idClasse).orElse(null);
        List<User> users = new ArrayList<>();


        if (classe != null) {
            for (String idUser : classe.getUsersIds()) {
                User user = userRepository.findById(idUser).orElse(null);
                if (user != null) {
                    if (user.getRole().equals(Role.STUDENT))
                        users.add(user);
                }

            }
        }


        return users;
    }

    @Override
    public List<User> getProfessorFromClass(String idClasse) {
        Classe classe = classeRepository.findById(idClasse).orElse(null);
        List<User> users = new ArrayList<>();


        if (classe != null) {
            for (String idUser : classe.getUsersIds()) {
                User user = userRepository.findById(idUser).orElse(null);
                if (user != null) {
                    if (user.getRole().equals(Role.PROFESSOR))
                        users.add(user);
                }

            }
        }


        return users;
    }

    @Override
    public Classe affecterUserInClass(String idUser, String idClasse) {
        Classe classe = classeRepository.findById(idClasse).orElse(null);
        User user = userRepository.findById(idUser).orElse(null);
        if (classe != null && user != null) {
            classe.getUsersIds().add(idUser);

            PhoneNumber to = new PhoneNumber("+21620954080");
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String m="Dear M/Ms "+user.getFirstName()+"\nAFFECT IN "+classe.getNameClasse()+","+classe.getSpecialite().getTitle();
            //Message message = Message.creator(to,from,m).create();

            return classeRepository.save(classe);
        }
        return null;
    }
    //**************************************************************



    @Override
    public List<User> getEtudiant() {
        List<User> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            if (user.getRole().equals(Role.STUDENT))
                users.add(user);

        }


        return users;
    }

    @Override
    public List<User> getEnsignat() {
        List<User> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            if (user.getRole().equals(Role.PROFESSOR))
                users.add(user);

        }


        return users;
    }









    }
