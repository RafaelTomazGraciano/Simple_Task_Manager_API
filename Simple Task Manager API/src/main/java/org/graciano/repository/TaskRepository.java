package org.graciano.repository;

import org.graciano.model.Task;
import org.graciano.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    public Task findById(long id) throws SQLException{
        String sql = "SELECT * FROM task WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return mapResultSetToTask(resultSet);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("Failed to find task with ID " + id, e);
        }
        return null;
    }

    public List<Task> findAll() throws SQLException{
        String sql = "SELECT * FROM task";
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()){
                tasks.add(mapResultSetToTask(resultSet));
            }
            return tasks;
        }catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("Failed to find task with ID " + e);
        }
    }

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

    private Task mapResultSetToTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getLong("id"));
        task.setTitle(resultSet.getString("title"));
        task.setDescription(resultSet.getString("description"));
        task.setDueDate(resultSet.getDate("due_date").toLocalDate());
        task.setStatus(resultSet.getString("status"));
        return task;
    }

}
