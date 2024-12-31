package com.dreamsource.taskmanager;

import com.dreamsource.taskmanager.task.Task;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TaskConfig {

    @Bean
        // remove when testing is over, resume when testing looks good and change in app properties (ddl-auto to update)
    CommandLineRunner commandLineRunner(TaskRepository repository) {
        return args -> {
            Task first = new Task("uno", "the first task");
            Task second = new Task("dos", "the second task", "2024-12-22T17:51:04.493381");
            repository.saveAll(List.of(first, second));
        };
    }
}
