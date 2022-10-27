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

//    @ManyToOne
//    @JsonBackReference
////    @JoinColumn(name = "assigner")  // ???????????
//    private User assigner;
//
//    @ManyToOne
//    @JsonBackReference
////    @JoinColumn(name = "assignee")
//    private User assignee;         //?????????????
//
////    @ManyToOne
////    @JsonBackReference
////    private User user;  //????????????
}
