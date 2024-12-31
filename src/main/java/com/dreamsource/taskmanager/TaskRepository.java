package com.dreamsource.taskmanager;

import com.dreamsource.taskmanager.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.title = ?1")
    List<Task> findTasksByTitle(String title);

    @Query("SELECT t FROM Task t WHERE t.id = ?1")
    Optional<Task> findTaskById(String id);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.id = ?1")
    int deleteTask(String id);
}
