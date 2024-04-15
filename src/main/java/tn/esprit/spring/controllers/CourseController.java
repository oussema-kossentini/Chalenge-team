package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Content;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
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
    UserRepository userRepository;
    // Define the expiration period for courses in minutes
    private static final long COURSE_EXPIRY_PERIOD_IN_MINUTES = 30; // Example: 30 minutes
    @PreAuthorize("hasAuthority('ADMINISTRATOR')  || hasAuthority('TEACHER') || hasAuthority('PROFESSOR')")
    @PostMapping("add/course")
    public Course addingBloc(Authentication authentication,@RequestBody Course us) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return courseService.addCourse(us);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("/retrieve-all-courses")
    public List<Course> getCourses(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Course> listCourses;
        listCourses = courseService.retrieveAllCourses();
        return listCourses;
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR')  || hasAuthority('TEACHER') || hasAuthority('PROFESSOR')")
    @DeleteMapping("/remove-Course/{course-id}")
    public void removeChambre(Authentication authentication,@PathVariable("course-id") String chId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        courseService.removeCourse(chId);
    }

    // http://localhost:8080/tpfoyer/chambre/modify-chambre
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || hasAuthority('TEACHER') ||  hasAuthority('PROFESSOR')")
    @PutMapping("/modify-cours/{id}")
    public Course modifyCourse(Authentication authentication,@PathVariable String id, @RequestBody Course c) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        c.setIdCourse(id);
        return courseService.addCourse(c);

    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR')  || hasAuthority('TEACHER')  || hasAuthority('PROFESSOR')")
    @PostMapping("/{courseId}/contents")
    public ResponseEntity<Course> addContentToCourse(Authentication authentication,@PathVariable String courseId, @RequestBody Content content) {

        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        try {
            Course updatedCourse = courseService.addContentToCourse(courseId, content);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("/{courseId}/contentss")
    public ResponseEntity<Set<Content>> getContentsOfCourse(Authentication authentication,@PathVariable String courseId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Set<Content> contents = courseService.getContentsOfCourse(courseId);
        return ResponseEntity.ok(contents);
    }

    // Method to periodically check and remove expired courses
    //jasser
}
