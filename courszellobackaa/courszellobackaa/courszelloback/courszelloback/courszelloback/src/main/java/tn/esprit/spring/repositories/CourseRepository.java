package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course,String> {
}
