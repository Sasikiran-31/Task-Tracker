package org.example.todoservice.controller;

import java.util.List;

import org.example.todoservice.dto.TaskDTO;
import org.example.todoservice.dto.UserDTO;
import org.example.todoservice.model.Task;
import org.example.todoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private UserService userservice;

    
@GetMapping("{user_id}")
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable int user_id) {

        return new ResponseEntity<>(userservice.findall(user_id), HttpStatus.OK);
    }

    @PostMapping("{user_id}")
    public ResponseEntity<UserDTO> createTask(@PathVariable int user_id, @RequestBody Task task) {

        return new ResponseEntity<>(userservice.addTaskById(user_id, task), HttpStatus.ACCEPTED);
    }

}
