package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Content;
import tn.esprit.spring.entities.Course;

import java.util.List;
import java.util.Set;

public interface ICourseService {
    Course addCourse(Course course);
    public List<Course> retrieveAllCourses();
    public void removeCourse(String id);
    public Course modifyCourse(Course bloc);

    public Course getCourseById(String courseId);

    public Course saveCourse(Course course);
    public Course addContentToCourse(String courseId, Content content)  ;

    public Set<Content> getContentsOfCourse(String courseId) ;

    public Content getContentByIId(String contentId);
}
