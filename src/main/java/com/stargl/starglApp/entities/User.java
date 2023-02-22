package com.stargl.starglApp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stargl.starglApp.dtos.UserDto;
import com.stargl.starglApp.enums.Roles;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
        if (userDto.getStarNum() != 0) {
            this.starNum = userDto.getStarNum();
        }
    }

}
