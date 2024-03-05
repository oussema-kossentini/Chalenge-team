package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.Session;

import java.util.List;

public interface ISessionService {
    Session addSession(Session session);
    public List<Session> retrieveAllSessions();
    public void removeSession(String id);
    public Session modifySession(Session session);
}
