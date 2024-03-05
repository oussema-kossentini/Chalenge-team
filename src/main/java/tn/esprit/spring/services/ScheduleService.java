package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.repositories.CourseRepository;
import tn.esprit.spring.repositories.ScheduleRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService implements IScheduleService{
    ScheduleRepository scheduleRepository;
    @Override
    public Scheduel addSchedule(Scheduel scheduel) {
        return scheduleRepository.save(scheduel);

    }

    @Override
    public List<Scheduel> retrieveAllScheduels() {
        return scheduleRepository.findAll();

    }

    @Override
    public void removeScheduel(String id) {
        scheduleRepository.deleteById(id);

    }

    @Override
    public Scheduel modifyScheduel(Scheduel bloc) {
        return scheduleRepository.save(bloc);
    }
}
