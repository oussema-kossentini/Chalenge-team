package tn.esprit.spring.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.services.IUserService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/users")
public class UserController {
    IUserService userService;
    @PostMapping("add")
    public User addingBloc(@RequestBody User us){
        return userService.addUser(us);
    }
    @GetMapping("/retrieve-all-users")
    public List<User> getUsers() {
        List<User> listChambres = userService.retrieveAllUsers();
        return listChambres;
    }
    @DeleteMapping("/remove-user/{user-id}")
    public void removeUser(@PathVariable("user-id") String chId) {
        userService.removeUser(chId);
    }
    // http://localhost:8080/tpfoyer/chambre/modify-chambre
    @PutMapping("/modify-user/{id}")
    public User modifyUser(@PathVariable String id,@RequestBody User user) {
        user.setIdUser(id);
        return userService.addUser(user);

    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.retrieveProfessorById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
