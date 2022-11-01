//package com.stargl.starglApp.entities;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.stargl.starglApp.enums.MappedEnum;
//import com.stargl.starglApp.enums.Roles;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "Roles")
//@Data
//@MappedEnum(enumClass = Roles.class)
//public class Role {
//
//    @Id
//    @Column(name = "id", nullable = false, unique = true)
//    private int id;
//
//    @Column(name = "name", nullable = false, unique = true, length = 32)
//    private String name;
//
////    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
////    @JsonBackReference
////    private Set<User> userSet = new HashSet<>();
//
//
//
////    public Role(int id, String name) {
////        this.id = id;
////        this.name = name;
////    }
//}
