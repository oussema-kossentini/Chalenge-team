package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Classe;

import java.util.List;

@Repository
public interface ClasseRepository extends MongoRepository<Classe,String> {

    List<Classe> findBySpecialite(String idSpecialite);


}
