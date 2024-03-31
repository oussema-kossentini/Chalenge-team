package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Scheduel;

import java.util.List;

public interface IScheduleService {
    Scheduel addSchedule(Scheduel scheduel);
    public List<Scheduel> retrieveAllScheduels();
    public void removeScheduel(String id);
    public Scheduel modifyScheduel(Scheduel bloc);
    public Scheduel retrieveScheduelById( String id) ;
    Scheduel addScheduelToClasseP(Scheduel scheduel, String idClasse);
}
