package com.stargl.starglApp.dtos;

import com.stargl.starglApp.entities.User;
import com.stargl.starglApp.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private Long id;

    private Roles role;

    private String username;

    private String password;

    private Long parentId;

    private int starNum;

    Set<TaskDto> taskDtoSetAssignee = new HashSet<>();

    Set<TaskDto> taskDtoSetAssigner = new HashSet<>();

    public UserDto(User user) {
        if (user.getId() != null) {
            this.id = user.getId();
        }
        if (user.getUsername() != null) {
            this.username = user.getUsername();
        }
        if (user.getPassword() != null) {
            this.password = user.getPassword();
        }
        if (user.getRole() != null) {
            this.role = user.getRole();
        }
        if (user.getStarNum() != 0) {
            this.starNum = user.getStarNum();
        }
    }


}
