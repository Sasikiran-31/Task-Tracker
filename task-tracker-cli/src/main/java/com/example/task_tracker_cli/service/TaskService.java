package com.example.task_tracker_cli.service;


import java.io.IOException;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.task_tracker_cli.cli.model.Task;
import com.example.task_tracker_cli.cli.model.TaskStatus;
import com.example.task_tracker_cli.cli.repo.TaskRepositoryJson;

@Service
public class TaskService {
    private final TaskRepositoryJson repo;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "task-events";

    public TaskService(TaskRepositoryJson repo, KafkaTemplate<String, String> kafkaTemplate) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Task add(String title) {
        Task t = new Task(0, title);
        try {
            t = repo.save(t);
            sendEvent("TASK_CREATED", t);
            return t;
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public List<Task> list() { return repo.findAll(); }

    public Task updateTitle(long id, String title) {
        Task t = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        t.setTitle(title);
        try {
            t = repo.save(t);
            sendEvent("TASK_UPDATED", t);
            return t;
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public Task updateStatus(long id, TaskStatus status) {
        Task t = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        t.setStatus(status);
        try {
            t = repo.save(t);
            sendEvent("TASK_STATUS_CHANGED", t);
            return t;
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void delete(long id) {
        repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        try {
            repo.delete(id);
            sendEvent("TASK_DELETED", new Task(id, ""));
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    private void sendEvent(String type, Task t) {
        String payload = String.format("{\"type\":\"%s\",\"taskId\":%d,\"title\":\"%s\",\"status\":\"%s\"}",
                type, t.getId(), escape(t.getTitle()), t.getStatus());
        kafkaTemplate.send(new ProducerRecord<>(TOPIC, String.valueOf(t.getId()), payload));
    }

    private String escape(String s) { return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\""); }
}

