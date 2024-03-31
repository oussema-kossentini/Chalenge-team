package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.CourseRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService{
    CourseRepository courseRepository;

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> retrieveAllCourses() {
        return courseRepository.findAll();

    }

    @Override
    public void removeCourse(String id) {
        courseRepository.deleteById(id);

    }

    @Override
    public Course modifyCourse(Course bloc) {
        return courseRepository.save(bloc);
    }
}
