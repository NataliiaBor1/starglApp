package com.stargl.starglApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stargl.starglApp.dtos.UserDto;
import com.stargl.starglApp.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column
//    private String role;

    @Column(unique = true)
    private String username;

    @Column (nullable = false)
    private String password;

    @OneToMany(mappedBy = "assignee", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    private Set<Task> taskSetAssignee = new HashSet<>();

    @OneToMany(mappedBy = "assigner", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    private Set<Task> taskSetAssigner = new HashSet<>();

//    @ManyToOne
//    @JsonBackReference
//    private Role role;
//
//    @Column(name = "role_id", nullable = false, unique = false)
//    @Enumerated(EnumType.ORDINAL)
//    private String role;


    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "stars")
    private int starNum;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    private User parent;

    @OneToMany
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    private List<User> children;

//    @Column(name = "parent_id")
//    private Long parentId;
//
//    @Column(name = "children_amount")
//    private int childrenAmount;


    public User (UserDto userDto) {
        if (userDto.getUsername() != null) {
            this.username = userDto.getUsername();
        }
        if (userDto.getPassword() != null) {
            this.password = userDto.getPassword();
        }
        if (userDto.getRole() != null) {
            this.role = userDto.getRole();
        }
//        this.parentId = userDto.getParentId();
    }

    // additional page for stars.html
    // can be drop-down for choosing a kid
    // button give star


}
