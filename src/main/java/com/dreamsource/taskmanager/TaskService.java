package com.dreamsource.taskmanager;

import com.dreamsource.taskmanager.exceptions.TaskNotFoundException;
import com.dreamsource.taskmanager.task.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }

    public List<Task> getTaskByTitle(String title) {
        List<Task> tasksByTitle = taskRepository.findTasksByTitle(title);
        if (!tasksByTitle.isEmpty()) {
            return taskRepository.findTasksByTitle(title);
        } else {
            throw new TaskNotFoundException("title: " + title);
        }
    }

    public Optional<Task> getTaskById(String id) {
        Optional<Task> taskById = taskRepository.findTaskById(id);
        if (taskById.isPresent()) {
            return taskRepository.findTaskById(id);
        } else {
            throw new TaskNotFoundException("id: " + id);
        }
    }

    public void addTask(Task task) {
        try {
            if (task.getTitle() == null || task.getTitle().isEmpty()) {
                throw new IllegalArgumentException("Task title cannot be null or empty");
            }
            taskRepository.save(task);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }

    @Transactional
    public void updateTask(Task task) {
        Optional<Task> taskById = taskRepository.findTaskById(task.getId().toString());
        if (taskById.isPresent()) {
            Task updatedTask = new Task(taskById.get().getTitle(), taskById.get().getDescription());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setDateToComplete(task.getDateToComplete());
            taskRepository.save(task);
        } else {
            throw new TaskNotFoundException("id: " + task.getId());
        }
    }

    @Transactional
    public void deleteTask(String id) {
        int deletedCount = taskRepository.deleteTask(id);
        if (deletedCount == 0) {
            throw new TaskNotFoundException("id: " + id);
        }
    }
}
