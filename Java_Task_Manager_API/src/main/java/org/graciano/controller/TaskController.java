package org.graciano.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import org.graciano.model.Task;
import org.graciano.service.TaskService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final TaskService taskService = new TaskService();

    public TaskController() {
        this.mapper.registerModule(new JavaTimeModule());
    }

    public void handleGetTaskById(HttpExchange exchange, long id) throws IOException {
        try {
            Task task = taskService.findById(id);
            if(task != null){
                String jsonResponse = mapper.writeValueAsString(task);
                sendResponse(exchange, 200, jsonResponse);
            }else {
                sendResponse(exchange, 404, "{\"error\": \"Task not found\"}");
            }
        }catch (Exception e){
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\": \"Internal Server Error\"}");
        }
    }

    public void handleListTasks(HttpExchange exchange) throws IOException {
        try {
            List<Task> tasks = taskService.findAll();
            String jsonResponse = mapper.writeValueAsString(tasks);
            sendResponse(exchange, 200, jsonResponse);
        }catch (Exception e){
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\": \"Internal Server Error\"}");
        }
    }

    public void handleCreateTask(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        try {
            //Read JSON
            InputStream requestBody = exchange.getRequestBody();
            //Deserialize JSON
            Task task = mapper.readValue(requestBody, Task.class);
            //Create Task
            Task createdTask = taskService.create(task);
            //Serialize JSON
            String jsonResponse = mapper.writeValueAsString(createdTask);
            //Send Response
            sendResponse(exchange, 200, jsonResponse);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            sendResponse(exchange, 400, "{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\": \"Internal Server Error\"}");
        }
    }

    public void handleUpdateTask(HttpExchange exchange, long id) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        try {
            InputStream requestBody = exchange.getRequestBody();
            Task task = mapper.readValue(requestBody, Task.class);
            task.setId(id);
            Task updatedtask = taskService.update(task);

            if (updatedtask != null) {
                String jsonResponse = mapper.writeValueAsString(updatedtask);
                sendResponse(exchange, 200, jsonResponse);
            }else  {
                sendResponse(exchange, 400, "{\"error\": \"Task not found\"}");
            }
        }catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
            sendResponse(exchange, 400, "{\"error\": \"" + e.getMessage() + "\"}");
        }catch (Exception e){
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\": \"Internal Server Error\"}");
        }
    }

    public void handleDeleteTask(HttpExchange exchange, long id) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        try {
            if (taskService.delete(id)){
                sendResponse(exchange, 200, "Task with id " + id + " deleted successfully");
            }else {
                sendResponse(exchange, 400, "{\"error\": \"Task not found\"}");
            }
        }catch (Exception e){
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\": \"Internal Server Error\"}");
        }
    }

    public void sendResponse(HttpExchange exchange, int status, String body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, body.length());
        OutputStream os = exchange.getResponseBody();
        os.write(body.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

}
