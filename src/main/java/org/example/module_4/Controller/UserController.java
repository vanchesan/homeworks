package org.example.module_4.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.module_4.DTO.UserForm;
import org.example.module_4.Entity.User;
import org.example.module_4.Service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping( "/api/add")
    public void addUser(@RequestBody UserForm user) {
        userService.addUser(user);

    }

    @GetMapping("/api/all")
    public List<User> getAllUsers() {
       return userService.getAllUsers();
    }

    @GetMapping("/api")
    public User getUserById(@RequestParam Integer id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/api")
    public void deleteUserById(@RequestParam Integer id) {
        userService.deleteUserById(id);
    }

    @PutMapping("api/{id}")
    public void updateUser(@RequestBody UserForm user, @PathVariable int id) {
        userService.updateUser(user, id);
    }
}
