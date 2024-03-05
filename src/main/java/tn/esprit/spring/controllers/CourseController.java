package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.services.ICourseService;
import tn.esprit.spring.services.IUserService;

<<<<<<< HEAD
import java.util.List;

@RestController
=======

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
>>>>>>> 83682a4 (Initial commit)
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/courses")
public class CourseController {
    ICourseService courseService;
    @PostMapping("add/course")
    public Course addingBloc(@RequestBody Course us){
        return courseService.addCourse(us);
    }
<<<<<<< HEAD
    @GetMapping("/retrieve-all-chambres")
    public List<Course> getCourses() {
        List<Course> listCourses = courseService.retrieveAllCourses();
=======

    @GetMapping("/retrieve-all-courses")
    public List<Course> getCourses() {
        List<Course> listCourses;
        listCourses = courseService.retrieveAllCourses();
>>>>>>> 83682a4 (Initial commit)
        return listCourses;
    }
    @DeleteMapping("/remove-Course/{course-id}")
    public void removeChambre(@PathVariable("course-id") String chId) {
        courseService.removeCourse(chId);
    }
    // http://localhost:8080/tpfoyer/chambre/modify-chambre
<<<<<<< HEAD
    @PutMapping("/modify-chambre/{id}")
=======
    @PutMapping("/modify-cours/{id}")
>>>>>>> 83682a4 (Initial commit)
    public Course modifyCourse(@PathVariable String id,@RequestBody Course c) {
        c.setIdCourse(id);
        return courseService.addCourse(c);

    }

}
