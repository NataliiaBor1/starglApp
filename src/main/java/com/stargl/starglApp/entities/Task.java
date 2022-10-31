package com.stargl.starglApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stargl.starglApp.dtos.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne
    @JsonBackReference
    private User assigner;

    @ManyToOne
    @JsonBackReference
    private User assignee;         //?????????????


    public Task(TaskDto taskDto) {
        if (taskDto.getTitle() != null) {
            this.title = taskDto.getTitle();
        }
        if (taskDto.getBody() != null) {
            this.body = taskDto.getBody();
        }
    }
}
