package org.example.todoservice.controller;

import org.example.todoservice.DTOS.UserDTO;
import org.example.todoservice.model.User;
import org.example.todoservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class userController {

    private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping("user/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(id);
        return "User deleted";
    }

    @DeleteMapping("/user/clear")
    public String deleteAllUsers() {
        userService.clearAll();
        return "All users deleted";
    }

}
