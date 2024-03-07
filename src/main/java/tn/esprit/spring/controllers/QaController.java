package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.QA;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.services.IQAService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:4200/**")
@RequestMapping("api/qa")
public class QaController {
    IQAService iqaService;
    @PostMapping("/add")
    public ResponseEntity<QA> addqa(@RequestBody QA qa){
   return ResponseEntity.ok(iqaService.addQA(qa));
    }
    @GetMapping("/retrieve-all-qa")
    public List<QA> getQas() {
        List<QA> listChambres = iqaService.retrieveAllQA();
        return listChambres;
    }
    @DeleteMapping("/remove-qa/{qa-id}")
    public void removeQa(@PathVariable("qa-id") String chId) {
        iqaService.removeQa(chId);
    }
    @PutMapping("/modify-qa/{id}")
    public QA modifyQa(@PathVariable String id,@RequestBody QA c) {
        c.setIdQa(id);
        return iqaService.addQA(c);
    }
    @PutMapping("assign-QA-Evaluation/{qaId}/{evaluationId}")
    public void assignToQaToEvaluation(@PathVariable("qaId")String qaId,@PathVariable("evaluationId") String evaluationId){
        this.iqaService.assignQaToEvaluation(qaId,evaluationId);
    }
    @GetMapping("getQaByEvaluationId/{evaluationId}")
    public ResponseEntity<List<QA>>getQaByEvaluationId(@PathVariable String evaluationId){
        return ResponseEntity.ok(this.iqaService.getQaByEvaluationId(evaluationId));
    }
}
