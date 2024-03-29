package com.stargl.starglApp.dtos;

import com.stargl.starglApp.entities.Task;
import com.stargl.starglApp.enums.Statuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto implements Serializable {


    private Long id;

    private String title;

    private String body;

    private Statuses status;

    private UserDto assigner;

    private UserDto assignee;

    private LocalDateTime startDate;

    private LocalDateTime finalDate;

    private LocalDateTime dueDate;


    public TaskDto(Task task) {
        if (task.getId() != null) {
            this.id = task.getId();
        }
        if (task.getBody() != null) {
            this.body = task.getBody();
        }
        if (task.getTitle() != null) {
            this.title = task.getTitle();
        }
        if (task.getAssignee() != null) {
            this.assignee = new UserDto(task.getAssignee());
        }
        if (task.getAssigner() != null) {
            this.assigner = new UserDto(task.getAssigner());
        }
        if (task.getStatus() != null) {
            this.status = task.getStatus();
        }
        if (task.getStartDate() != null) {
            this.startDate = task.getStartDate();
        }
        if (task.getFinalDate() != null) {
            this.finalDate = task.getFinalDate();
        }
        if (task.getDueDate() != null) {
            this.dueDate = task.getDueDate();
        }
    }

}
