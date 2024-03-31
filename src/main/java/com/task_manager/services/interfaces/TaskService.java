package com.task_manager.services.interfaces;

import com.task_manager.domain.*;
import com.task_manager.domain.SimpleTask;

import java.util.List;

public interface TaskService {

    /**
     * Create a new task
     * @param registerTask {@link RegisterTask}
     * @return Task
     */
    TaskCreateResponse createTask(RegisterTask registerTask);

    /**
     * Update a task
     * @param updateTask {@link UpdateTask}
     * @return Task
     */
    TaskResponse updateTask(UpdateTask updateTask);

    /**
     * Update status from task
     * @param updateStatus {@link UpdateStatus}
     * @return Task
     */
    TaskResponse updateStatus(UpdateStatus updateStatus);

    /**
     * Find all task by id author
     * @param id {@link Long}
     * @return Things to do
     */
    List<TaskResponse> findAll(Long id);

    /**
     * Find one task by id author
     * @param onlyTask {@link SimpleTask}
     * @return Task
     */
    TaskResponse findByIdAuthor(SimpleTask onlyTask);

    /**
     * Delete one task
     * @param onlyTask {@link SimpleTask}
     */
    void deleteTask(SimpleTask onlyTask);
}
