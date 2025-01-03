package com.dreamsource.taskmanager;

import com.dreamsource.taskmanager.exceptions.TaskNotFoundException;
import com.dreamsource.taskmanager.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Task>> getTaskByTitle(@PathVariable("title") String title) {
        List<Task> tasks = taskService.getTaskByTitle(title);
        return ResponseEntity.ok(tasks);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") String id) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID: " + id + " not found"));
        return ResponseEntity.ok(task);
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task '" + task.getTitle() + "' added successfully");
    }

    @PutMapping(value = "/")
    public ResponseEntity<?> updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}


