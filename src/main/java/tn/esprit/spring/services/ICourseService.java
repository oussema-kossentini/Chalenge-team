package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Course;

import java.util.List;

public interface ICourseService {
    Course addCourse(Course course);
    public List<Course> retrieveAllCourses();
    public void removeCourse(String id);
    public Course modifyCourse(Course bloc);
}
