package com.stargl.starglApp.repositories;

import com.stargl.starglApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
