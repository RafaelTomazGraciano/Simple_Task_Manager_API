package org.graciano.repository;

import org.graciano.model.Task;
import org.graciano.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class TaskRepository {

    public Task createTask(Task task){
        String sql = "INSERT INTO task (title, description, due_date, status) VALUES (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setDate(3, Date.valueOf(task.getDueDate()));
            statement.setString(4, task.getStatus());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()){
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    task.setId(generatedId);
                }
            }

            return task;

        }catch(SQLException e){
            System.err.println("Error saving task in databse: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

//    public void update(Task task){
//
//    }
//
//    public void delete(Task task){
//
//    }
//
//    public List<Task> findAll(){
//
//    }

}
