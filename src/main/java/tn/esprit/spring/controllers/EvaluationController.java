package tn.esprit.spring.controllers;

import jakarta.websocket.server.PathParam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.services.EmailService;
import tn.esprit.spring.services.IEvaluationService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:4200/**")
@RequestMapping("api/evaluations")
public class EvaluationController {
    IEvaluationService evaluationService;
    EmailService emailService;

    @PostMapping("/addeval")
    public Evaluation addeval(@RequestBody Evaluation evaluation) {
        long duration = evaluation.getEndDate().getTime() - evaluation.getStartDate().getTime();
        evaluation.setDuration(duration/1000);
        return evaluationService.addEvaluation(evaluation);
    }

    @GetMapping("/retrieve-all-evaluations")
    public List<Evaluation> getChambres() {
        List<Evaluation> listEvaluations = evaluationService.retrieveAllEvaluations();
        return listEvaluations;
    }

    @GetMapping("{evaluationId}")
    public ResponseEntity<Evaluation> getEvalution(@PathVariable("evaluationId") String evaluationId)
    {
        Evaluation evaluation = evaluationService.getEvaluationById(evaluationId);
        return ResponseEntity.ok(evaluation);
    }
    @DeleteMapping("/remove-evaluation/{evaluation-id}")
    public void removeEvaluation(@PathVariable("evaluation-id") String chId) {
        evaluationService.removeEvaluation(chId);
    }

    @PutMapping("/modify-evaluation/{id}")
    public Evaluation modifyEvaluation(@PathVariable String id, @RequestBody Evaluation c) {
        c.setIdEvaluation(id);
        return evaluationService.addEvaluation(c);

    }

    @PostMapping("/assign-evaluation-to-user/{userId}/{evaluationId}")
    public User assignEvaluationToUser(@PathVariable String userId, @PathVariable String evaluationId) {
        return evaluationService.assignEvaluationToUser(userId, evaluationId);
    }

    @PostMapping("/assign-evaluation-to-class/{classeId}/{evaluationId}")
    public Evaluation assignEvaluationToClasse(@PathVariable String classeId, @PathVariable String evaluationId){
        return evaluationService.assignEvaluationToClasse(classeId,evaluationId);
    }


    @GetMapping("/evaluations/{userId}")
    public List<Evaluation> getEvaluationsByUserAndCategory(
            @PathVariable String userId,
            @RequestParam String category) {
        return evaluationService.getEvaluationsByUserAndCategory(userId, category);
    }


}
