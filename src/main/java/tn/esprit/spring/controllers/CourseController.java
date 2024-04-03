package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Content;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.services.ICourseService;
import tn.esprit.spring.services.IUserService;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/courses")
public class CourseController {
    ICourseService courseService;

    // Define the expiration period for courses in minutes
    private static final long COURSE_EXPIRY_PERIOD_IN_MINUTES = 30; // Example: 30 minutes

    @PostMapping("add/course")
    public Course addingBloc(@RequestBody Course us) {
        return courseService.addCourse(us);
    }

    @GetMapping("/retrieve-all-courses")
    public List<Course> getCourses() {
        List<Course> listCourses;
        listCourses = courseService.retrieveAllCourses();
        return listCourses;
    }

    @DeleteMapping("/remove-Course/{course-id}")
    public void removeChambre(@PathVariable("course-id") String chId) {
        courseService.removeCourse(chId);
    }

    // http://localhost:8080/tpfoyer/chambre/modify-chambre
    @PutMapping("/modify-cours/{id}")
    public Course modifyCourse(@PathVariable String id, @RequestBody Course c) {
        c.setIdCourse(id);
        return courseService.addCourse(c);

    }

    @PostMapping("/{courseId}/contents")
    public ResponseEntity<Course> addContentToCourse(@PathVariable String courseId, @RequestBody Content content) {
        try {
            Course updatedCourse = courseService.addContentToCourse(courseId, content);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{courseId}/contentss")
    public ResponseEntity<Set<Content>> getContentsOfCourse(@PathVariable String courseId) {
        Set<Content> contents = courseService.getContentsOfCourse(courseId);
        return ResponseEntity.ok(contents);
    }

    // Method to periodically check and remove expired courses

}
