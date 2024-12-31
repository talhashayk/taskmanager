package com.dreamsource.taskmanager.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Task {

    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    private Long id;
    private String title;
    private String description;
    private LocalDateTime datePublished;
    private LocalDateTime dateToComplete;

    @Transient
    private String timeRemaining;

    private static final int DEFAULT_DAYS_To_COMPLETE = 3;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.datePublished = LocalDateTime.now();
        this.dateToComplete = LocalDateTime.now().plusDays(DEFAULT_DAYS_To_COMPLETE);
    }

    public Task(String title, String description, String selectedCompletionDate) {
        this.title = title;
        this.description = description;
        this.datePublished = LocalDateTime.now();
        this.dateToComplete = LocalDateTime.parse(selectedCompletionDate);
    }

    @PostLoad
    @PostPersist
    public void ensureDefaults() {
        if (this.datePublished == null) {
            this.datePublished = LocalDateTime.now();
        }
        if (this.dateToComplete == null) {
            this.dateToComplete = this.datePublished.plusDays(DEFAULT_DAYS_To_COMPLETE);
        }
    }

    public String getTimeRemaining() {
        long timeRemaining = Duration.between(LocalDateTime.now(), dateToComplete).toDays();
        return timeRemaining >= 0 ? String.valueOf(timeRemaining) : "Expired";
    }
}
