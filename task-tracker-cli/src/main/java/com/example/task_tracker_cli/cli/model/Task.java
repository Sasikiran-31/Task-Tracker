package com.example.task_tracker_cli.cli.model;

import java.time.Instant;

public class Task {
    private long id;
    private String title;
    private TaskStatus status = TaskStatus.PENDING;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public Task() {}
    public Task(long id, String title) { this.id = id; this.title = title; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}

