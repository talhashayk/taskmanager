package com.dreamsource.taskmanager;

import com.dreamsource.taskmanager.exceptions.TaskNotFoundException;
import com.dreamsource.taskmanager.task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void whenSearchingForTaskById_givenValidTaskId_shouldReturnTask() {
        String taskId = "1";
        Task mockTask = new Task("Test Task", "Test Description");
        mockTask.setId(Long.valueOf(taskId));

        when(taskRepository.findTaskById(taskId)).thenReturn(Optional.of(mockTask));

        Task result = taskService.getTaskById(taskId).orElseThrow();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Long.valueOf(taskId), result.getId());
        Assertions.assertEquals("Test Task", result.getTitle());
        Assertions.assertEquals("Test Description", result.getDescription());
    }

    @Test
    void whenSearchingForTaskById_givenTaskIdIsNotSet_shouldReturnTaskNotFoundException() {
        String taskId = "99";

        when(taskRepository.findTaskById(taskId)).thenReturn(Optional.empty());

        TaskNotFoundException exception = Assertions.assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(taskId)
        );

        Assertions.assertEquals("Task with id: 99 not found", exception.getMessage());
    }
}
