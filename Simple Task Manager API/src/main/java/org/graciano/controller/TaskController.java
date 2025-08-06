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

public class TaskController implements HttpHandler {
    private final ObjectMapper mapper = new ObjectMapper();
    private final TaskService taskService = new TaskService();

    public TaskController() {
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        long id = -1;

        if (path.matches("/tasks/\\+d")) {
            String idStr = path.substring(path.indexOf("/")+1);
            id = Long.parseLong(idStr);
        }

        try {
            switch (method) {
//                case "GET":
//                    if(id != 1) {
//                        handleGetTaskById(exchange, id);
//                    }else {
//                        handleListTasks(exchange);
//                    }
//                    break;

                case "POST":
                    handleCreateTask(exchange);
                    break;

//                case "PUT":
//                    if (id != 1){
//                        handleupdateTask(exchange, id);
//                    }else {
//                        sendResponse(exchange, 400, "{\"error\": \"Missing task ID\"}")
//                    }
//                    break;
//
//                case "DELETE":
//                    if (id != 1){
//                        handleDeleteTask(exchange, id);
//                    }else{
//                        sendResponse(exchange, 400, "{\"error\": \"Missing task ID\"}")
//                    }
//                    break;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            sendResponse(exchange, 400, "{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
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
