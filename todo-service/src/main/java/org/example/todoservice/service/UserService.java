package org.example.todoservice.service;

import jakarta.transaction.Transactional;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.todoservice.DTOS.TaskDTO;
import org.example.todoservice.DTOS.UserDTO;
import org.example.todoservice.model.Task;
import org.example.todoservice.model.User;
import org.example.todoservice.repository.TaskRepo;
import org.example.todoservice.repository.UserRepo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "task-events";

    private void sendMessage(String eventType, TaskDTO taskDTO) {
        String payload = String.format("{\"Event\"Type\":\"\"%s\",\"taskId\":%d,\"TaskDecription\":\"%s\"}",
                eventType, taskDTO.taskid(), taskDTO.task_description());
        kafkaTemplate.send(new ProducerRecord<>(TOPIC, String.valueOf(taskDTO.taskid()), payload));

    }


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepo userRepo, TaskRepo taskRepo,  KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
        this.kafkaTemplate = kafkaTemplate;
    }


    public UserDTO save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return new UserDTO(user.getUsername(), user.getUserId());
    }

    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public UserDTO addTaskById(int userId, Task task) {
            User user = userRepo.findById(userId)
                    .orElseThrow();
                user.addTask(task);
                taskRepo.save(task);
                sendMessage("TASK-CREATED", new TaskDTO(task.getTaskId(), task.getDescription()));
                return new UserDTO(user.getUsername(), user.getUserId());

    }

    public List<TaskDTO> findall(int userId) {
        User user = userRepo.findById(userId).orElseThrow();
        List<Task> tasks = user.getTasks();
        return tasks.stream().map(task -> new TaskDTO(task.getTaskId(), task.getDescription())).collect(Collectors.toList());
    }

    public List<UserDTO> getAll() {
        List<User> users = userRepo.findAll();
        return users.stream().map(user -> new UserDTO(user.getUsername(), user.getUserId())).collect(Collectors.toList());

    }

    public void clearAll() {
        List<User> users = userRepo.findAll();

        for (User user : users) {
            if(!user.getUsername().equals("admin")){
                userRepo.deleteUserByUsername(user.getUsername());
            }
        }
    }

}