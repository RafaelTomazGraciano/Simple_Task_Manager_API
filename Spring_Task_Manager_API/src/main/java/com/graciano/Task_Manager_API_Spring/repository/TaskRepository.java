package com.graciano.Task_Manager_API_Spring.repository;

import com.graciano.Task_Manager_API_Spring.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

}
