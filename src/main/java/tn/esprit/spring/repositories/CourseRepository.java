package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Course;

import java.util.Date;
import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course,String> {
    List<Course> findByDebutDateBefore(Date date);
}
