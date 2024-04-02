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


//orgin
        if (subjectOptional.isPresent()&& ScheduelOptional.isPresent()) {
            Scheduel scheduel = ScheduelOptional.get();
            Subject subject = subjectOptional.get();



            session.setSchedule(scheduel);
            session.setSubject(subject);

            return sessionRepository.save(session);
        } else {
            throw new RuntimeException("Classe avec id " + idScheduel + " non trouvée"+idSubject);
        }
    }

    @Override
    public List<Session> retrieveAllSessions() {
        return sessionRepository.findAll();

    }



    @Transactional
    public Session addSessionScheduel(Session session, String idScheduel) {


        Optional<Scheduel> scheduelOptional = scheduleRepository.findById(idScheduel);

        if (scheduelOptional.isPresent()) {
            Scheduel scheduel = scheduelOptional.get();

            session.setSchedule(scheduel);

            return sessionRepository.save(session);
        } else {
            throw new RuntimeException("Classe avec id " + idScheduel + " non trouvée");
        }

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
