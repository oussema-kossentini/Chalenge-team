package tn.esprit.spring.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Userr;
import tn.esprit.spring.exceptions.UserAlreadyExistException;
import tn.esprit.spring.exceptions.UserNotFoundException;
import tn.esprit.spring.services.UserrService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserrController {

    @Autowired
    private UserrService userService;

    @GetMapping("/getall")
    public ResponseEntity<List<Userr>> getall() throws IOException {
        try{
            return new ResponseEntity<List<Userr>>(userService.getall(), HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity("User not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Userr> addUser(@RequestBody Userr user) throws IOException {
        try{
            return new ResponseEntity<Userr>(userService.addUser(user), HttpStatus.OK);
        }catch (UserAlreadyExistException e){
            return new ResponseEntity("User already exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getbyusername/{username}")
    public ResponseEntity<Userr> getUserByUserName(@PathVariable String username) throws IOException {
        try{
            return new ResponseEntity<Userr>(userService.getUserByUserName(username), HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity("User not Found", HttpStatus.NOT_FOUND);
        }
    }

}
