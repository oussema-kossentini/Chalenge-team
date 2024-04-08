package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.Session;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.repositories.ScheduleRepository;
import tn.esprit.spring.repositories.SessionRepository;
import tn.esprit.spring.repositories.SubjectRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService implements ISessionService{
    SessionRepository sessionRepository;
    ScheduleRepository scheduleRepository ;
    SubjectRepository subjectRepository;
  /*  @Transactional
    public boolean addSession(Session session, String idScheduel, String idSubject) {
        Optional<Scheduel> scheduelOptional = scheduleRepository.findById(idScheduel);
        Optional<Subject> subjectOptional = subjectRepository.findById(idSubject);

        if (!scheduelOptional.isPresent() || !subjectOptional.isPresent()) {
            return false; // Scheduel ou Subject non trouvé
        }

        Scheduel scheduel = scheduelOptional.get();
        Subject subject = subjectOptional.get();

        // Vérifie si la session chevauche une autre session existante
        boolean isOverlapping = isSessionOverlap(scheduel.getIdScheduel(), session.getDay(), session.getDebutHour(), session.getEndHour());
        if (isOverlapping) {
            session.setAvailability(false);
            // Vous pouvez également décider de ne pas enregistrer la session si elle n'est pas disponible
            // Pour l'instant, nous l'enregistrons avec availability à false
            sessionRepository.save(session);
            return false; // Chevauchement détecté, session non disponible
        } else {
            session.setSchedule(scheduel);
            session.setSubject(subject);
            session.setAvailability(true); // Aucun chevauchement, session disponible
            sessionRepository.save(session);
            return true; // Session ajoutée avec succès
        }
    }

    @Transactional
    public boolean isSessionOverlap(String idScheduel, String day, LocalTime debutHour, LocalTime endHour) {
        List<Session> sessions = sessionRepository.findByScheduleIdAndDay(idScheduel, day);
        return sessions.stream().anyMatch(existingSession ->
                debutHour.isBefore(existingSession.getEndHour()) && endHour.isAfter(existingSession.getDebutHour())
        );
    }*/

    @Transactional
    public Session addSession(Session session, String idScheduel, String idSubject) {
        Optional<Subject> subjectOptional = subjectRepository.findById(idSubject);
        Optional<Scheduel> ScheduelOptional = scheduleRepository.findById(idScheduel);

        if (subjectOptional.isPresent() && ScheduelOptional.isPresent()) {
            Scheduel schedule = ScheduelOptional.get();
            Subject subject = subjectOptional.get();

            // Vérification de l'existence d'une session au même horaire, jour et vérification que le planning est différent
            List<Session> existingSessions = sessionRepository.findByDayAndDebutHourAndEndHour(
                    session.getDay(), session.getDebutHour(), session.getEndHour());

            boolean isConflict = existingSessions.stream().anyMatch(existingSession ->
                    existingSession.getSchedule() != null && existingSession.getSchedule().getIdScheduel().equals(idScheduel));

            if (isConflict) {
                throw new RuntimeException("Ce créneau horaire est déjà réservé pour le jour " + session.getDay() + " dans ce planning spécifique.");
            }

            session.setSchedule(schedule);
            session.setSubject(subject);

            return sessionRepository.save(session);
        } else {
            throw new RuntimeException("Sujet avec id " + idSubject + " ou Planning avec id " + idScheduel + " non trouvé.");
        }
    }



    @Override
    public List<Session> retrieveAllSessions() {
        return sessionRepository.findAll();

    }



    @Transactional
    public Session addSessionScheduel(Session session, String idScheduel) {
        // Récupère le Scheduel par son ID
        Optional<Scheduel> scheduelOptional = scheduleRepository.findById(idScheduel);

        if (!scheduelOptional.isPresent()) {
            throw new RuntimeException("Classe avec id " + idScheduel + " non trouvée");
        }
        Scheduel scheduel = scheduelOptional.get();

        // Vérifie si une session existe déjà pour le jour, l'heure de début et de fin spécifiés
        List<Session> existingSessions = sessionRepository.findByDayAndDebutHourAndEndHour(
                session.getDay(), session.getDebutHour(), session.getEndHour());

        boolean isConflict = existingSessions.stream().anyMatch(existingSession ->
                existingSession.getSchedule() != null && existingSession.getSchedule().getIdScheduel().equals(idScheduel));

        if (isConflict) {
            throw new RuntimeException(" Choisissez une autre date car elle est déjà remplie.");
        }

        // Si aucune session existante n'est trouvée pour le créneau horaire, on l'ajoute
        session.setSchedule(scheduel);
        return sessionRepository.save(session);
    }





    @Override
    public void removeSession(String id) {
        sessionRepository.deleteById(id);


    }

    @Override
    public Session modifySession(Session session) {
        return sessionRepository.save(session);
    }
}
