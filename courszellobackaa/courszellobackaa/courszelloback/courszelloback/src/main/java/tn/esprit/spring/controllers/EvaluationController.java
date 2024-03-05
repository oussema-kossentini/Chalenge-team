package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Evaluation;
import tn.esprit.spring.entities.QA;
import tn.esprit.spring.services.IEvaluationService;
import tn.esprit.spring.services.IQAService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/evaluations")
public class EvaluationController {
    IEvaluationService evaluationService;
    @PostMapping("add/eval ")
    public Evaluation addqa(@RequestBody Evaluation evaluation){
        return evaluationService.addEvaluation(evaluation);
    }
    @GetMapping("/retrieve-all-evaluations")
    public List<Evaluation> getChambres() {
        List<Evaluation> listEvaluations = evaluationService.retrieveAllEvaluations();
        return listEvaluations;
    }
    @DeleteMapping("/remove-evaluation/{evaluation-id}")
    public void removeEvaluation(@PathVariable("evaluation-id") String chId) {
        evaluationService.removeEvaluation(chId);
    }

    @PutMapping("/modify-evaluation/{id}")
    public Evaluation modifyEvaluation(@PathVariable String id,@RequestBody Evaluation c) {
        c.setIdEvaluation(id);
        return evaluationService.addEvaluation(c);

    }
}
