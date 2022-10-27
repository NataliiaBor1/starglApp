package com.stargl.starglApp.repositories;

import com.stargl.starglApp.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
