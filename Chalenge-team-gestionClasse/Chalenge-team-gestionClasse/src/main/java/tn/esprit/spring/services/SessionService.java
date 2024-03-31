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
import tn.esprit.spring.repositories.SubjectRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService implements ISessionService{
    SessionRepository sessionRepository;
    ScheduleRepository scheduleRepository ;
    SubjectRepository subjectRepository;
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
