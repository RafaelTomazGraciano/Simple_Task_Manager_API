package org.graciano;

import com.sun.net.httpserver.HttpServer;
import org.graciano.controller.RoutesController;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            //register routes
            server.createContext("/tasks", new RoutesController());

            server.setExecutor(null);
            server.start();
            System.out.println("Server started in http://localhost:8080/tasks");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}