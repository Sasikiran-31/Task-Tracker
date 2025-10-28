package com.example.task_tracker_cli.cli;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.task_tracker_cli.cli.model.Task;
import com.example.task_tracker_cli.cli.model.TaskStatus;
import com.example.task_tracker_cli.service.TaskService;

import picocli.CommandLine;


@Component
public class TaskCommands {
    private final TaskService service;

    public TaskCommands( TaskService service) {
        this.service = service;
    }

    public void execute(String[] args) {
        CommandLine cmd = new CommandLine(new Root(service));
        int exit = cmd.execute(args);
        if(exit!=0) {
            System.exit(exit);
        }
    }

    @CommandLine.Command(name = "task", mixinStandardHelpOptions = true, description = "Task CLI with Spring Kafka")
    static class Root implements Runnable {
        private final TaskService service;
        Root(TaskService service) { this.service = service; }
        public void run() { new CommandLine(this).usage(System.out); }

        @CommandLine.Command(name = "add", description = "Add a new task")
        void add(@CommandLine.Parameters(paramLabel = "TITLE", description = "Task title") String title) {
            com.example.task_tracker_cli.cli.model.Task t = service.add(title);
            System.out.printf("Added: #%d %s%n", t.getId(), t.getTitle());
        }

        @CommandLine.Command(name = "list", description = "List tasks")
        void list() {
            List<Task> tasks = service.list();
            if (tasks.isEmpty()) { System.out.println("No tasks."); return; }
            tasks.forEach(t -> System.out.printf("#%d [%s] %s%n", t.getId(), t.getStatus(), t.getTitle()));
        }

        @CommandLine.Command(name = "update", description = "Update task title")
        void update(@CommandLine.Parameters(paramLabel = "ID") long id,
                    @CommandLine.Parameters(paramLabel = "NEW_TITLE") String title) {
            Task t = service.updateTitle(id, title);
            System.out.printf("Updated: #%d -> %s%n", t.getId(), t.getTitle());
        }

        @CommandLine.Command(name = "complete", description = "Mark task as DONE")
        void complete(@CommandLine.Parameters(paramLabel = "ID") long id) {
            Task t = service.updateStatus(id, TaskStatus.DONE);
            System.out.printf("Completed: #%d%n", t.getId());
        }

        @CommandLine.Command(name = "delete", description = "Delete a task")
        void delete(@CommandLine.Parameters(paramLabel = "ID") long id) {
            service.delete(id);
            System.out.printf("Deleted: #%d%n", id);
        }
    }
}
