package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Scheduel;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    UserRepository userRepository;
    @Override
    public User addUser(User user) {


            return userRepository.save(user);
    }


    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();

    }

    @Override
    public void removeUser(String id) {
        userRepository.deleteById(id);

    }

    @Override
    public User modifyUser(User updatedUser) {

        return userRepository.save(updatedUser);


    }
    @Override
    public User retrieveProfessorById(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getRole() == Role.PROFESSOR) {
                return user;
            }
        }
        return null; // Si aucun utilisateur n'est trouvé avec le rôle de professeur ou si aucun utilisateur n'est trouvé avec cet ID
    }




}
