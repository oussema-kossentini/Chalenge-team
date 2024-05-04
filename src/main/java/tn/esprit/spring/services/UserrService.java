package tn.esprit.spring.services;


import tn.esprit.spring.entities.Userr;
import tn.esprit.spring.exceptions.UserAlreadyExistException;
import tn.esprit.spring.exceptions.UserNotFoundException;

import java.util.List;

public interface UserrService {
    List<Userr> getall() throws UserNotFoundException;

    Userr addUser(Userr user) throws UserAlreadyExistException;

    Userr getUserByUserName(String username)  throws UserNotFoundException;
}
