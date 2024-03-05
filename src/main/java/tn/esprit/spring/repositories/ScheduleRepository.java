package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Scheduel;

@Repository
public interface ScheduleRepository extends MongoRepository<Scheduel,String> {
}
