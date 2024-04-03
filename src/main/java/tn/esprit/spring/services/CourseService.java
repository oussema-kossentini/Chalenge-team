package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Content;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.CourseRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;

    // Define the expiration period for courses in minutes
    private static final long COURSE_EXPIRY_PERIOD_IN_MINUTES = 30; // Example: 30 minutes

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
    public Course modifyCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course addContentToCourse(String courseId, Content content) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.getContents().add(content);
            return courseRepository.save(course);
        } else {
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }
    }

    @Override
    public Set<Content> getContentsOfCourse(String courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            return course.getContents();
        } else {
            throw new RuntimeException("Course with ID " + courseId + " not found");
        }
    }

    // Method to periodically check and remove expired courses
    // Method to periodically check and remove expired courses
    @Scheduled(fixedRate = 1000) // Runs every minute
    public void printMessage() {
        System.out.println("Scheduled task executed at: " + new java.util.Date());
        List<Course> courses = retrieveAllCourses();
        Date currentDate = new Date();

        for (Course course : courses) {
            Date endDate = course.getEndDate(); // Get the end date of the course

            if (currentDate.after(endDate)) { // If current date is after the end date
                removeCourse(course.getIdCourse()); // Remove the course
            }
        }
    }
}
