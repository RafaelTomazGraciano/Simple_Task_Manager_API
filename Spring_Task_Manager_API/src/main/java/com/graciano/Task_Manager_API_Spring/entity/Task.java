package com.graciano.Task_Manager_API_Spring.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull
    @FutureOrPresent(message = "Due Date cannot be in the past")
    private LocalDate dueDate;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
