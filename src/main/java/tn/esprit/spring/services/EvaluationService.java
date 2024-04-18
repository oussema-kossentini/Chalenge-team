package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.EvaluationAttempts;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.repositories.EvaluationAttemptsRepository;
import tn.esprit.spring.repositories.EvaluationRepository;
import tn.esprit.spring.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class EvaluationService implements IEvaluationService {
    EvaluationRepository evaluationRepository;
    UserRepository userRepository;
    ClasseRepository classeRepository;
    EvaluationAttemptsRepository evaluationAttemptsRepository;
    EmailService emailService;

    @Override
    public Evaluation addEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    //new
    @Override
    @Transactional
    public void addEvaluationAttempt(String userId, String evaluationId, long score) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("could not found user with id ," + userId));
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new RuntimeException("could not found evaluation with id" + evaluationId));
        EvaluationAttempts evaluationAttempts = new EvaluationAttempts(null, user, evaluation, score);
        evaluationAttemptsRepository.save(evaluationAttempts);
        emailService.
                sendEmail(user.getEmail(), "Evaluation Result", "Hello " + user.getFirstName() + " " + user.getLastName() + "\n"
                        + "Your Score for " + evaluation.getTitle() + " is : " + score);
        if (evaluation.getOwner() != null) {
            emailService.
                    sendEmail(evaluation.getOwner().getEmail(), "Evaluation Submitted", "Hello " + evaluation.getOwner().getFirstName() + " " + "\n"
                            + "the total score of  " + user.getFirstName() + " " + user.getLastName() + " is : " + score);
        }

    }
    //new
    public List<EvaluationAttempts> getAllEvalAttempts() {
        return evaluationAttemptsRepository.findAll();
    }

    public User assignEvaluationToUser(String userId, String evaluationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));

        if (user.getEvaluations() == null) {
            user.setEvaluations(new ArrayList<>());
        }
        user.getEvaluations().add(evaluation);
        userRepository.save(user);
        emailService.
                sendEmail(user.getEmail(), "Evaluation link ", "http://localhost:4200/evaluation-assessment/" + userId + "/" + evaluationId);
        return user;
    }

    @Override
    public Evaluation getEvaluationById(String evaluationId) {
        return evaluationRepository.findById(evaluationId).orElseThrow(() ->
                new RuntimeException("Evaluation Not Found"));
    }

    @Override
    public Evaluation assignEvaluationToClasse(String classeId, String evaluationId) {
        Evaluation evaluation = getEvaluationById(evaluationId);
        Classe aClass = classeRepository.findById(classeId).orElseThrow(() -> new RuntimeException("could not find class"));
        Set<User> users = aClass.getSpecialite()
                .getUsers();
        users.forEach(user -> this.emailService.sendEmail(user.getEmail(), "Evaluation link ", "http://localhost:4200/evaluation-assessment/" + user.getIdUser() + "/" + evaluationId));
        evaluation.setClasse(aClass);
        return evaluationRepository.save(evaluation);

    }


    @Override
    public List<Evaluation> retrieveAllEvaluations() {
        return evaluationRepository.findAll();

    }

    @Override
    @Transactional
    public void removeEvaluation(String id) {
        evaluationRepository.deleteById(id);

    }

    public void updateEvaluationAccessibility() {
        List<Evaluation> evaluations = evaluationRepository.findAll();
        for (Evaluation evaluation : evaluations) {
            evaluation.setAccessible(isEvaluationAccessible(evaluation));
            evaluationRepository.save(evaluation);
        }
    }

    private boolean isEvaluationAccessible(Evaluation evaluation) {
        Date now = new Date();
        return evaluation.getStartDate().before(now) && evaluation.getEndDate().after(now);
    }

    @Override
    public Evaluation modifyEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }




    //new
    @Override
    public List<EvaluationAttempts> getEvaluationAttemptsByUserId(String userId) {
        return evaluationAttemptsRepository.findAllByUserIdIdUser(userId);
    }


}
