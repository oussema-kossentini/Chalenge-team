package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.QA;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;
import tn.esprit.spring.services.IQAService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:4200/**")
@RequestMapping("/api/qa")
public class QaController {
    IQAService iqaService;
    UserRepository userRepository;
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @PostMapping("/add")
    public ResponseEntity<QA> addqa(Authentication authentication,@RequestBody QA qa){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
   return ResponseEntity.ok(iqaService.addQA(qa));
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("/retrieve-all-qa")
    public List<QA> getQas(Authentication authentication) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<QA> listChambres = iqaService.retrieveAllQA();
        return listChambres;
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || hasAuthority('TEACHER') || hasAuthority('PROFESSOR')")
    @DeleteMapping("/remove-qa/{qa-id}")
    public void removeQa(Authentication authentication,@PathVariable("qa-id") String chId) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        iqaService.removeQa(chId);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR')|| hasAuthority('TEACHER') || hasAuthority('PROFESSOR')")
    @PutMapping("/modify-qa/{id}")
    public QA modifyQa(Authentication authentication,@PathVariable String id,@RequestBody QA c) {
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        c.setIdQa(id);
        return iqaService.addQA(c);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @PutMapping("assign-QA-Evaluation/{qaId}/{evaluationId}")
    public void assignToQaToEvaluation(Authentication authentication,@PathVariable("qaId")String qaId,@PathVariable("evaluationId") String evaluationId){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        this.iqaService.assignQaToEvaluation(qaId,evaluationId);
    }
    @PreAuthorize("hasAuthority('ADMINISTRATOR') || (hasAuthority('USER') || hasAuthority('TEACHER') || hasAuthority('STUDENT') || hasAuthority('PROFESSOR'))")
    @GetMapping("getQaByEvaluationId/{evaluationId}")
    public ResponseEntity<List<QA>>getQaByEvaluationId(Authentication authentication,@PathVariable String evaluationId){
        String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return ResponseEntity.ok(this.iqaService.getQaByEvaluationId(evaluationId));
    }

}
