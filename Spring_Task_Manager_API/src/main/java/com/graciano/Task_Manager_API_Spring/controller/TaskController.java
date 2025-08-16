package com.graciano.Task_Manager_API_Spring.controller;

import com.graciano.Task_Manager_API_Spring.entity.Task;
import com.graciano.Task_Manager_API_Spring.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping
    public List<Task> getTasks(){
        return taskService.findAll();
    }

    @PostMapping
    public Task createTask(@Valid @RequestBody Task task){
        return taskService.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@Valid @RequestBody Task task, @PathVariable Long id){
        return taskService.update(task, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteById(id);
    }

}
