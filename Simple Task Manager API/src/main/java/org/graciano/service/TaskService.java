package org.graciano.service;

import org.graciano.model.Task;
import org.graciano.repository.TaskRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class TaskService {

    private final TaskRepository taskRepository =  new TaskRepository();
    private static final Set<String> VALID_STATUS = Set.of("pending", "in_progress", "done");

    public TaskService() {
    }

    public Task findById(long id) {
        try {
            return taskRepository.findById(id);
        }catch (SQLException e){
            throw new RuntimeException("Error fetching task from database", e);
        }
    }

    public List<Task> findAll(){
        try {
            return taskRepository.findAll();
        }catch (SQLException e){
            throw new RuntimeException("Error fetching all tasks from databse", e);
        }
    }

    public Task create(Task task) throws SQLException{
        validate(task);
        return taskRepository.createTask(task);
    }

    public void validate(Task task) throws IllegalArgumentException{
        if(task.getTitle() == null || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Title is required");
        }
        if (task.getDueDate() == null){
            throw new IllegalArgumentException("Due Date is required");
        }
        if(task.getDueDate().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Due Date cannot be in the past");
        }
        if (task.getStatus() == null || !VALID_STATUS.contains(task.getStatus())){
            throw new IllegalArgumentException("Invalid Status. Valid status are: " + VALID_STATUS);
        }
    }

}
