package tn.esprit.spring.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.User;

import java.util.List;

public interface IUserService {




    User addUser(User user);
   User addUserimage(User user, MultipartFile image) ;
    public List<User> retrieveAllUsers();
    public void removeUser(String id);

    public boolean requestPasswordReset(String email);
    public boolean verifyResetCode(String email, String code);

    User modifyUser(String userId, User userStr, MultipartFile image);

}
