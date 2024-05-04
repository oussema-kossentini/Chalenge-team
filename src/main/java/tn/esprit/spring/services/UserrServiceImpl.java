package tn.esprit.spring.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Userr;
import tn.esprit.spring.exceptions.UserAlreadyExistException;
import tn.esprit.spring.exceptions.UserNotFoundException;
import tn.esprit.spring.repositories.UserrRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserrServiceImpl implements UserrService {

    @Autowired
    private UserrRepository userRepository;


    @Override
    public List<Userr> getall() throws UserNotFoundException {
        List<Userr> users=userRepository.findAll();
        if (users.isEmpty()){
            throw new UserNotFoundException();
        }else {
           return users;
        }
    }

    @Override
    public Userr addUser(Userr user) throws UserAlreadyExistException {
       Optional<Userr> user1=userRepository.findById(user.getUserName());

       if (user1.isPresent()){
           throw new UserAlreadyExistException();
       }else {
           return userRepository.save(user);
       }
    }

    @Override
    public Userr getUserByUserName(String username) throws UserNotFoundException {
        Optional<Userr> user1=userRepository.findById(username);

        if (user1.isPresent()){
            return user1.get();
        }else {
            throw new UserNotFoundException();
        }
    }

}
