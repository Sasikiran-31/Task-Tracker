package org.example.todoservice.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name="users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int userId;

    private String username;
    private String password;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<Task> tasks;

    public void addTask(Task task){
        tasks.add(task);
        task.setUser(this);
        
    }
}
