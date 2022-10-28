package com.stargl.starglApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "text")
    private String body;

    @ManyToOne
    @JsonBackReference
//    @JoinColumn(name = "assigner")  // ???????????
    private User assigner;

    @ManyToOne
    @JsonBackReference
//    @JoinColumn(name = "assignee")
    private User assignee;         //?????????????
//
////    @ManyToOne
////    @JsonBackReference
////    private User user;  //????????????


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Task(Integer id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
