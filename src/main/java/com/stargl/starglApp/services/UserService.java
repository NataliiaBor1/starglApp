package com.stargl.starglApp.services;

import com.stargl.starglApp.dtos.UserDto;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    @Transactional
    List<String> registerUser(UserDto userDto);

    List<String> userLogin(UserDto userDto);

    List<UserDto> getAllChildrenByUserId(Long parentId);

}
