package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.Session;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface SessionRepository extends MongoRepository<Session,String> {


    List<Session> findByDayAndDebutHourAndEndHour(String day, LocalTime debutHour, LocalTime endHour);

    boolean existsByDayAndDebutHourAndEndHour(String day, LocalTime debutHour, LocalTime endHour);
}
