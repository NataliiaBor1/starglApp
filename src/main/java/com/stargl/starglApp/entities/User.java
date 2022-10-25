package com.stargl.starglApp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column(unique = true)
    private String username;

    @Column (nullable = false, length = 30)
    private String password;

    @OneToMany
    @JsonManagedReference(mappedBy = "user")
    private Set<Task> taskSet = new HashSet<>();

    @Column
    @OneToOne(mappedBy = "role")
    private int role_id;




}
