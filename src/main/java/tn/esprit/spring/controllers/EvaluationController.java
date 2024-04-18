package tn.esprit.spring.controllers;

import jakarta.websocket.server.PathParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.EvaluationAttempts;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.EmailService;
import tn.esprit.spring.services.IEvaluationService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:4200/**")
@RequestMapping("/api/evaluations")
public class EvaluationController {
    IEvaluationService evaluationService;
    UserRepository userRepository;
    EmailService emailService;
    @PreAuthorize("hasAuthority('ADMINISTRATOR')  || hasAuthority('TEACHER') || hasAuthority('PROFESSOR')")
    @PostMapping("/addeval")
    public Evaluation addeval(Authentication authentication,@RequestBody Evaluation evaluation) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        long duration = evaluation.getEndDate().getTime() - evaluation.getStartDate().getTime();
        evaluation.setDuration(duration/1000);
        return evaluationService.addEvaluation(evaluation);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') ||  hasAuthority('TEACHER')  || hasAuthority('PROFESSOR')")
    @GetMapping("/retrieve-all-evaluations")
    public List<Evaluation> getChambres(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<Evaluation> listEvaluations = evaluationService.retrieveAllEvaluations();
        return listEvaluations;
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("{evaluationId}")
    public ResponseEntity<Evaluation> getEvalution(Authentication authentication,@PathVariable("evaluationId") String evaluationId)
    {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Evaluation evaluation = evaluationService.getEvaluationById(evaluationId);
        return ResponseEntity.ok(evaluation);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || hasAuthority('TEACHER') || hasAuthority('PROFESSOR')")
    @DeleteMapping("/remove-evaluation/{evaluation-id}")
    public void removeEvaluation(Authentication authentication,@PathVariable("evaluation-id") String chId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        evaluationService.removeEvaluation(chId);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || hasAuthority('TEACHER') || hasAuthority('PROFESSOR')")
    @PutMapping("/modify-evaluation/{id}")
    public Evaluation modifyEvaluation(Authentication authentication,@PathVariable String id, @RequestBody Evaluation c) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        c.setIdEvaluation(id);
        return evaluationService.addEvaluation(c);

    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @PostMapping("/assign-evaluation-to-user/{userId}/{evaluationId}")
    public User assignEvaluationToUser(Authentication authentication,@PathVariable String userId, @PathVariable String evaluationId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return evaluationService.assignEvaluationToUser(userId, evaluationId);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @PostMapping("/assign-evaluation-to-class/{classeId}/{evaluationId}")
    public Evaluation assignEvaluationToClasse(Authentication authentication,@PathVariable String classeId, @PathVariable String evaluationId){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return evaluationService.assignEvaluationToClasse(classeId,evaluationId);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @PostMapping("addEvaluationAttempt")
    public void addEvaluationAttempt(Authentication authentication,@RequestParam String userId, String evaluationId, long score) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        evaluationService.addEvaluationAttempt(userId, evaluationId, score);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    //new
    @GetMapping("getAllEvaluationAttempts")
    public List<EvaluationAttempts> getAllEvalAttempts(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return evaluationService.getAllEvalAttempts();
    }

    //new
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("getEvaluationAttemptsByUserId/{userId}")
    public List<EvaluationAttempts> getEvaluationAttemptsByUserId(Authentication authentication,@PathVariable("userId") String userId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return evaluationService.getEvaluationAttemptsByUserId(userId);
    }

}