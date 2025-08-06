package org.graciano.model;


import java.time.LocalDate;

public class Task {
    private long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;

    public Task() {}

    public Task(long id, String title, String description, LocalDate due_date, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = due_date;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }
}
