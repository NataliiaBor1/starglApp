package com.stargl.starglApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stargl.starglApp.dtos.TaskDto;
import com.stargl.starglApp.enums.Roles;
import com.stargl.starglApp.enums.Statuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column(columnDefinition = "text")
    private String body;

    @Column(name = "status", length = 25)
    @Enumerated(EnumType.STRING)
    private Statuses status;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "final_date")
    private LocalDateTime finalDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne
    @JsonBackReference
    private User assigner;

    @ManyToOne
    @JsonBackReference
    private User assignee;

    public Task(TaskDto taskDto) {
        if (taskDto.getTitle() != null) {
            this.title = taskDto.getTitle();
        }
        if (taskDto.getBody() != null) {
            this.body = taskDto.getBody();
        }
        if (taskDto.getStatus() != null) {
            this.status = taskDto.getStatus();
        }
        if (taskDto.getDueDate() != null) {
            this.dueDate = taskDto.getDueDate();
        }
    }
}
