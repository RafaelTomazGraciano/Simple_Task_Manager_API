package com.graciano.Task_Manager_API_Spring.service;


import com.graciano.Task_Manager_API_Spring.entity.Task;
import com.graciano.Task_Manager_API_Spring.exceptions.TaskNotFoundException;
import com.graciano.Task_Manager_API_Spring.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task update(Task updateTask, Long id) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        //Update
        existingTask.setTitle(updateTask.getTitle());
        existingTask.setDescription(updateTask.getDescription());
        existingTask.setDueDate(updateTask.getDueDate());
        existingTask.setStatus(updateTask.getStatus());

        return taskRepository.save(existingTask);
    }

    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

}
