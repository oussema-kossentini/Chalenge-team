package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.services.ICourseService;
import tn.esprit.spring.services.IScheduleService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/schedule")
public class ScheduleController {
    IScheduleService scheduleService;
    @PostMapping("add/schedule")
    public Scheduel addschedule(@RequestBody Scheduel us){
        return scheduleService.addSchedule(us);
    }
    @GetMapping("/retrieve-all-scheduels")
    public List<Scheduel> getChambres() {
        List<Scheduel> listChambres = scheduleService.retrieveAllScheduels();
        return listChambres;
    }
    @DeleteMapping("/remove-scheduel/{scheduel-id}")
    public void removeChambre(@PathVariable("scheduel-id") String chId) {
        scheduleService.removeScheduel(chId);
    }
    @PutMapping("/modify-scheduel/{id}")
    public Scheduel modifyScheduel(@PathVariable String id,@RequestBody Scheduel c) {
        c.setIdScheduel(id);
        return scheduleService.addSchedule(c);

    }
}
