package org.example.todoservice.controller;

import org.example.todoservice.DTOS.TaskDTO;
import org.example.todoservice.DTOS.UserDTO;
import org.example.todoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @RequestMapping("api/root")
public class rootController {


    @Autowired
    private UserService service;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable int userId) {
        return new ResponseEntity<>(service.findall(userId), HttpStatus.OK);
    }
}
