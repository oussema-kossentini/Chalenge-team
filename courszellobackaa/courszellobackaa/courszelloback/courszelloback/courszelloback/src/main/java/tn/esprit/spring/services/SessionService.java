package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Session;
import tn.esprit.spring.repositories.ScheduleRepository;
import tn.esprit.spring.repositories.SessionRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SessionService implements ISessionService{
    SessionRepository sessionRepository;
    @Override
    public Session addSession(Session session) {
        return sessionRepository.save(session);
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
}
