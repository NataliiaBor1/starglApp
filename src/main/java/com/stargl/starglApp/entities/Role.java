//package com.stargl.starglApp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "Role")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Role {
//
//    @Id
//    private Integer id;
//
//    @Column
//    private String name;
//
//    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JsonBackReference
//    private Set<User> userSet = new HashSet<>();
//
//
//
//    public Role(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//}
