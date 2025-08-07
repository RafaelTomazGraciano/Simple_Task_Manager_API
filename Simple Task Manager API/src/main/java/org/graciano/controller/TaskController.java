package org.graciano.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.graciano.model.Task;
import org.graciano.service.TaskService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskController implements HttpHandler {
    private final ObjectMapper mapper = new ObjectMapper();
    private final TaskService taskService = new TaskService();

    public TaskController() {
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        long id = -1;
        String[] segments = path.split("/");

        if (segments.length == 3 && segments[2].matches("\\d+")) {
            try {
                id = Long.parseLong(segments[2]);
            } catch (NumberFormatException e) {
                sendResponse(exchange, 400, "{\"error\": \"Invalid ID format\"}");
                return;
            }
        }

        try {
            switch (method) {
                case "GET":
                    if (id != -1) {
                        handleGetTaskById(exchange, id);
                    } else { 
                        handleListTasks(exchange);
                    }
                    break;

                case "POST":
                    if (id == -1) {
                        handleCreateTask(exchange);
                    } else {
                        sendResponse(exchange, 400, "{\"error\": \"Invalid request for POST\"}");
                    }
                    break;

                case "PUT":
                    if (id != -1) {
                        // handleUpdateTask(exchange, id);
                    } else {
                        sendResponse(exchange, 400, "{\"error\": \"Missing task ID\"}");
                    }
                    break;

                case "DELETE":
                    if (id != -1) {
                        // handleDeleteTask(exchange, id);
                    } else {
                        sendResponse(exchange, 400, "{\"error\": \"Missing task ID\"}");
                    }
                    break;
                default:
                    sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
                    break;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            sendResponse(exchange, 400, "{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\": \"Internal Server Error\"}");
        }
    }

    private void handleGetTaskById(HttpExchange exchange, long id) throws IOException {
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


    private void handleCreateTask(HttpExchange exchange) throws IOException {
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

    private void sendResponse(HttpExchange exchange, int status, String body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, body.length());
        OutputStream os = exchange.getResponseBody();
        os.write(body.getBytes(StandardCharsets.UTF_8));
        os.close();
    }


}
