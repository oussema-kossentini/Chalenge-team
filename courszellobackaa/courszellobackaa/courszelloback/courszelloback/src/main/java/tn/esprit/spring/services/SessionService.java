package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.Session;
import tn.esprit.spring.entities.Subject;
import tn.esprit.spring.repositories.ScheduleRepository;
import tn.esprit.spring.repositories.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tn.esprit.spring.repositories.SubjectRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService implements ISessionService{
    SessionRepository sessionRepository;
    ScheduleRepository scheduleRepository;
    SubjectRepository subjectRepository;



    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ScheduleNotFoundException extends RuntimeException {
        public ScheduleNotFoundException(String message) {
            super(message);
        }
    }

   /* @Transactional
    public Session addSession(Session session, String idScheduel) {
        session = sessionRepository.save(session);

        Optional<Scheduel> scheduelOptional = scheduleRepository.findById(idScheduel);

        if (scheduelOptional.isPresent()) {
            Scheduel scheduel = scheduelOptional.get();

            scheduel.getSessions().add(session);

            session.setSchedule(scheduel);
            session = sessionRepository.save(session);

            scheduleRepository.save(scheduel);

            return session;
        } else {
            throw new RuntimeException("Scheduel avec iddd " + idScheduel + " non trouvé");
        }
    }*/


   @Transactional
    public Session addSession(Session session, String idScheduel, String idSubject) {
        // Enregistre d'abord la session sans la lier à un Scheduel ou Subject
        session = sessionRepository.save(session);

        // Récupère le Scheduel et le Subject en utilisant leurs ID
        Optional<Scheduel> scheduelOptional = scheduleRepository.findById(idScheduel);
        Optional<Subject> subjectOptional = subjectRepository.findById(idSubject);

        if (!scheduelOptional.isPresent()) {
            throw new RuntimeException("Scheduel avec id " + idScheduel + " non trouvé");
        }
        if (!subjectOptional.isPresent()) {
            throw new RuntimeException("Subject avec id " + idSubject + " non trouvé");
        }

        // Assigne la session au Scheduel et au Subject trouvés
        Scheduel scheduel = scheduelOptional.get();
        Subject subject = subjectOptional.get();

        session.setSchedule(scheduel);
        session.setSubject(subject);

        // Met à jour les relations dans les entités concernées
        scheduel.getSessions().add(session);
        subject.getSessions().add(session);

        // Sauvegarde les modifications
        session = sessionRepository.save(session);
        scheduleRepository.save(scheduel);
        subjectRepository.save(subject);

        return session;
    }




    @Override
    public List<Session> retrieveAllSessions() {
        return sessionRepository.findAll();

    }

    @Override
    public void removeSession(String id) {
        sessionRepository.deleteById(id);


    }

    @Override
    public Session modifySession(Session session) {
        return sessionRepository.save(session);
    }

   /* @Override
    public Session addSubjectToSession(Session session, String idSubject) {
        Optional<Subject> subjectOptional = subjectRepository.findById(idSubject);

        if (subjectOptional.isPresent()) {
            Subject subject = subjectOptional.get();

            session.setSubject(subject);

            return sessionRepository.save(session);
        } else {
            throw new RuntimeException("Subject avec id " + idSubject + " non trouvée");
        }
    }*/
}
