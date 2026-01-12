package org.example.todoservice.controller;

import org.example.todoservice.DTOS.TaskDTO;
import org.example.todoservice.DTOS.UserDTO;
import org.example.todoservice.model.Task;
import org.example.todoservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final UserService userService;

    public TaskController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("{user_id}")
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable int user_id) {

        return new ResponseEntity<List<TaskDTO>>(userService.findall(user_id), HttpStatus.OK);
    }

    @PostMapping("{user_id}")
    public ResponseEntity<UserDTO> createTask(@PathVariable int user_id, @RequestBody Task task) {

        return new ResponseEntity<UserDTO>(userService.addTaskById(user_id, task), HttpStatus.ACCEPTED);
    }

}
