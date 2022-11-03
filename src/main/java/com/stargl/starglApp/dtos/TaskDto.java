package com.stargl.starglApp.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stargl.starglApp.entities.Task;
import com.stargl.starglApp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto implements Serializable {


    private Long id;

    private String title;

    private String body;

    private UserDto assigner;

    private UserDto assignee;         //?????????????

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
    }

}
