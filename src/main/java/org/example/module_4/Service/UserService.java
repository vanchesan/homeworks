package org.example.module_4.Service;

import org.example.module_4.DTO.UserForm;
import org.example.module_4.Entity.User;
import org.example.module_4.Repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;


    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addUser(UserForm user) {
        User newUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .created_at(LocalDate.now())
                .build();
        userRepo.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    public User getUserById(int id) {
        return userRepo.findById(id).orElseThrow();
    }
    public void deleteUserById(int id) {
        userRepo.deleteById(id);
    }
    public void deleteUser(User user) {
        userRepo.delete(user);
    }
    public void updateUser(UserForm user) {

    }
}

