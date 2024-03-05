package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.entities.QA;

import java.util.List;

public interface IQAService {
    QA addQA(QA qa);
    public List<QA> retrieveAllQA();
    public void removeQa(String id);
    public QA modifyQa(QA bloc);
}
