package tn.esprit.spring.auth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class CorsController {
    @RequestMapping(value = "/api/auth/update-image", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> handleOptions() {
        return ResponseEntity.ok().build();
    }
}
////
/////
/////
///update testi biha lel oumaima
///oussema




