package org.graciano.controller;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RoutesController implements HttpHandler {
    private final TaskController taskController = new TaskController();

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
                taskController.sendResponse(exchange, 400, "{\"error\": \"Invalid ID format\"}");
                return;
            }
        }

        try {
            switch (method) {
                case "GET":
                    if (id != -1) {
                        taskController.handleGetTaskById(exchange, id);
                    } else {
                        taskController.handleListTasks(exchange);
                    }
                    break;

                case "POST":
                    if (id == -1) {
                        taskController.handleCreateTask(exchange);
                    } else {
                        taskController.sendResponse(exchange, 400, "{\"error\": \"Invalid Request\"}");
                    }
                    break;

                case "PUT":
                    if (id != -1) {
                        taskController.handleUpdateTask(exchange, id);
                    } else {
                        taskController.sendResponse(exchange, 400, "{\"error\": \"Missing task ID\"}");
                    }
                    break;

                case "DELETE":
                    if (id != -1) {
                        taskController.handleDeleteTask(exchange, id);
                    } else {
                        taskController.sendResponse(exchange, 400, "{\"error\": \"Missing task ID\"}");
                    }
                    break;
                default:
                    taskController.sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
                    break;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            taskController.sendResponse(exchange, 400, "{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            taskController.sendResponse(exchange, 500, "{\"error\": \"Internal Server Error\"}");
        }
    }

}
