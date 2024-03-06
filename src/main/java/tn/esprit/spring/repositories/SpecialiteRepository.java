package tn.esprit.spring.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Specialite;

import java.util.List;

@Repository
public interface SpecialiteRepository extends MongoRepository<Specialite, String> {

    @Query("{ 'id' : ?0 }")
    Specialite findSpecialiteById(String idSpecialite);

    // Si vous voulez juste le titre, vous pouvez essayer de retourner un DTO ou un champ spécifique (nécessite une implémentation supplémentaire pour mapper le résultat)


}
