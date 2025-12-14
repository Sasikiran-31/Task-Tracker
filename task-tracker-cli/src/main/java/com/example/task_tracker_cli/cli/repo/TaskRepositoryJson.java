package com.example.task_tracker_cli.cli.repo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.task_tracker_cli.cli.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class TaskRepositoryJson {
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    private final Path filePath;
    private final Path backupDir;

    private final Map<Long, Task> tasks = new LinkedHashMap<>();
    private long sequence = 0;

    public TaskRepositoryJson(@Value("${task.storage.file:tasks.json}") String fileName,
                              @Value("${task.storage.backup-dir:.backups}") String backupDir) throws IOException {
        this.filePath = Path.of(fileName).toAbsolutePath();
        this.backupDir = Path.of(backupDir).toAbsolutePath();
        if (!Files.exists(this.backupDir)) Files.createDirectories(this.backupDir);
        load();
    }

    public synchronized List<Task> findAll() { return new ArrayList<>(tasks.values()); }
    public synchronized Optional<Task> findById(long id) { return Optional.ofNullable(tasks.get(id)); }

    public synchronized Task save(Task t) throws IOException {
        if (t.getId() == 0) t.setId(++sequence);
        t.setUpdatedAt(Instant.now());
        tasks.put(t.getId(), t);
        persist();
        return t;
    }

    public synchronized void delete(long id) throws IOException {
        tasks.remove(id);
        persist();
    }

    public synchronized void deleteAll() throws IOException{
       tasks.clear();
       persist();
    }

private void load() throws IOException {
    if (!Files.exists(filePath)) return;
    try {
        List<Task> list = mapper.readValue(filePath.toFile(), new TypeReference<>(){});
        for (Task t : list) {
            tasks.put(t.getId(), t);
            sequence = Math.max(sequence, t.getId());
        }
    } catch (Exception e) {
        System.err.println("Warning: Failed to load tasks.json, starting fresh. Cause: " + e.getMessage());
        // Optionally move the bad file to backup
        Path corrupted = backupDir.resolve("corrupted-" + System.currentTimeMillis() + ".json");
        Files.move(filePath, corrupted);
    }
}


    private void persist() throws IOException {
        File f = filePath.toFile();
        // backup
        if (f.exists()) {
            Path backup = backupDir.resolve("tasks-" + System.currentTimeMillis() + ".json");
            Files.copy(filePath, backup);
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(f, tasks.values());
    }
}
