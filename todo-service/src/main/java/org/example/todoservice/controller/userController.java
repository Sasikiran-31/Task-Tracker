package org.example.todoservice.controller;

import jakarta.servlet.http.HttpSession;
import org.example.todoservice.DTOS.UserDTO;
import org.example.todoservice.model.User;
import org.example.todoservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class userController {

    private final UserService userService;
    private final AuthenticationManager authManager;

    public userController(UserService userService, AuthenticationManager authManager) {
        this.userService = userService;
        this.authManager = authManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user),  HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (auth.isAuthenticated()){
            return "Successfully logged in";
        } else {
            return "Failed";
        }
    }



    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(HttpSession httpSession) {
        System.out.println(httpSession.getId());
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
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
