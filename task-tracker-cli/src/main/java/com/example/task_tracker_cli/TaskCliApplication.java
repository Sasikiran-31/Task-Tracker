package com.example.task_tracker_cli;


import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.task_tracker_cli.cli.model.Task;
import com.example.task_tracker_cli.cli.model.TaskStatus;
import com.example.task_tracker_cli.service.TaskService;

@SpringBootApplication
public class TaskCliApplication implements CommandLineRunner {

    private final TaskService service;

    public TaskCliApplication(TaskService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskCliApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Task Manager Menu ===");
            System.out.println("1. List tasks");
            System.out.println("2. Add task");
            System.out.println("3. Update task title");
            System.out.println("4. Mark task as DONE");
            System.out.println("5. Delete task");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        List<Task> tasks = service.list();
                        if (tasks.isEmpty()) {
                            System.out.println("No tasks found.");
                        } else {
                            tasks.forEach(t ->
                                    System.out.printf("#%d [%s] %s%n", t.getId(), t.getStatus(), t.getTitle()));
                        }
                        break;

                    case "2":
                        System.out.print("Enter task title: ");
                        String title = scanner.nextLine();
                        Task newTask = service.add(title);
                        System.out.printf("Added: #%d %s%n", newTask.getId(), newTask.getTitle());
                        break;

                    case "3":
                        System.out.print("Enter task ID: ");
                        long updateId = Long.parseLong(scanner.nextLine());
                        System.out.print("Enter new title: ");
                        String newTitle = scanner.nextLine();
                        Task updated = service.updateTitle(updateId, newTitle);
                        System.out.printf("Updated: #%d -> %s%n", updated.getId(), updated.getTitle());
                        break;

                    case "4":
                        System.out.print("Enter task ID: ");
                        long doneId = Long.parseLong(scanner.nextLine());
                        Task doneTask = service.updateStatus(doneId, TaskStatus.DONE);
                        System.out.printf("Completed: #%d%n", doneTask.getId());
                        break;

                    case "5":
                        System.out.print("Enter task ID: ");
                        long deleteId = Long.parseLong(scanner.nextLine());
                        service.delete(deleteId);
                        System.out.printf("Deleted: #%d%n", deleteId);
                        break;

                    case "0":
                        running = false;
                        System.out.println("Exiting Task Manager. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid option, try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
