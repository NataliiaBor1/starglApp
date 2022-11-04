package com.stargl.starglApp.repositories;

import com.stargl.starglApp.entities.Task;
import com.stargl.starglApp.entities.User;
import org.apache.catalina.LifecycleState;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


     List<Task> findAllByAssignerEquals(User user);

     List<Task> findAllByAssigneeEquals(User user);
}
