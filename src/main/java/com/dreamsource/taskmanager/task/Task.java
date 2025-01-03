package com.dreamsource.taskmanager.task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Task {

    private static final int DEFAULT_DAYS_TO_COMPLETE = 3;
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

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.datePublished = LocalDateTime.now();
        this.dateToComplete = LocalDateTime.now().plusDays(DEFAULT_DAYS_TO_COMPLETE);
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
            this.dateToComplete = this.datePublished.plusDays(DEFAULT_DAYS_TO_COMPLETE);
        }
    }

    public String getTimeRemaining() {
        long timeRemaining = Duration.between(LocalDateTime.now(), dateToComplete).toDays();
        return timeRemaining >= 0 ? String.valueOf(timeRemaining) : "Expired";
    }
}
