package tn.esprit.spring.services;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.User;

import java.util.List;

public interface IUserService {
    User addUser(User user);
    public List<User> retrieveAllUsers();
    public void removeUser(String id);
    public User modifyUser(User updatedUser);
    public User retrieveProfessorById(String id);

}
