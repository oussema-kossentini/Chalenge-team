package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.Session;
import tn.esprit.spring.entities.Subject;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends MongoRepository<Session,String> {



    List<Session> findByDayAndDebutHourAndEndHour(String day, LocalTime debutHour, LocalTime endHour);



}
