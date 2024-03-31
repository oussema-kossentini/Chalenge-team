package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.CourseRepository;
import tn.esprit.spring.repositories.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService implements IScheduleService{
    ScheduleRepository scheduleRepository;
    ClasseRepository classeRepository ;
    @Override
    public Scheduel addSchedule(Scheduel scheduel) {
        return scheduleRepository.save(scheduel);

    }

    @Override
    public Scheduel retrieveScheduelById(String id) {
        return scheduleRepository.findById(id).orElse(null);
    }


    @Transactional
    public Scheduel addScheduelToClasseP(Scheduel scheduel, String idClasse) {
        Optional<Classe> classeOptional = classeRepository.findById(idClasse);

        if (classeOptional.isPresent()) {
            Classe classe = classeOptional.get();

            scheduel.setClasse(classe);

            return scheduleRepository.save(scheduel);
        } else {
            throw new RuntimeException("Classe avec id " + idClasse + " non trouvée");
        }
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
