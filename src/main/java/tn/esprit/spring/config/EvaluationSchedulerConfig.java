package tn.esprit.spring.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.scheduling.annotation.Scheduled;
import tn.esprit.spring.services.EvaluationService;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class EvaluationSchedulerConfig {


    private final EvaluationService evaluationService;

   @Scheduled(fixedRate = 10000)
    public void checkEvaluationDeadlines() {

        evaluationService.updateEvaluationAccessibility();
    }
}