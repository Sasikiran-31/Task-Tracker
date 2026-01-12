package org.example.todoservice.controller;

import java.util.List;

import org.example.todoservice.dto.UserDTO;
import org.example.todoservice.model.User;
import org.example.todoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userservice;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userservice.getAll(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userservice.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping("user/{id}")
    public String deleteUser(@PathVariable int id) {
        userservice.delete(id);
        return "User deleted";
    }

    @DeleteMapping("/user/clear")
    public String deleteAllUsers() {
        userservice.clearAll();
        return "All users deleted";
    }

}
