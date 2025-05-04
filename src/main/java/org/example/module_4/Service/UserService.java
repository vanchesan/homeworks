package org.example.module_4.Service;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.example.module_4.DTO.UserForm;
import org.example.module_4.Entity.User;
import org.example.module_4.Repository.UserRepo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final KafkaProducerService kafkaProducerService;


    public void addUser(UserForm user) {
        User newUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .created_at(LocalDate.now())
                .build();
        userRepo.save(newUser);
        kafkaProducerService.send("CREATE", user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(int id) {
        return userRepo.findById(id).orElseThrow();
    }

    public void deleteUserById(int id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Пользователь не найден"));
        userRepo.deleteById(id);
        UserForm userForm = UserForm.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
        kafkaProducerService.send("DELETE", userForm);
    }

    public User updateUser(UserForm userForm, int id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Пользователь не найден"));
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setAge(userForm.getAge());
        return userRepo.save(user);
    }
}

