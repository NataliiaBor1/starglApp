package com.stargl.starglApp.services;

import com.stargl.starglApp.dtos.TaskDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    @Transactional
    List<String> addTask(TaskDto taskDto, Long parentId, Long childId);

    @Transactional
    void deleteTaskById(Long taskId);

    @Transactional
    void updateTaskById(TaskDto taskDto);

    Optional<TaskDto> getTaskById(Long taskId);

    List<TaskDto> getAllTasksByAssignerId(Long assignerId);

    List<TaskDto> getAllTasksByAssigneeId(Long assigneeId);
}
