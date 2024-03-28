package com.task_manager.services.interfaces;

import com.task_manager.domain.*;

import java.util.List;

public interface TaskService {

    /**
     * Create a new task
     * @param newTask {@link NewTask}
     * @return Task
     */
    Task createTask(NewTask newTask);

    /**
     * Update a task
     * @param updateTask {@link UpdateTask}
     * @return Task
     */
    Task updateTask(UpdateTask updateTask);

    /**
     * Update status from task
     * @param updateStatus {@link UpdateStatus}
     * @return Task
     */
    Task updateStatus(UpdateStatus updateStatus);

    /**
     * Find all task by id author
     * @param id {@link Long}
     * @return Things to do
     */
    List<Task> findAll(Long id);

    /**
     * Find one task by id author
     * @param onlyTask {@link OnlyTask}
     * @return Task
     */
    Task findByIdAuthor(OnlyTask onlyTask);

    /**
     * Delete one task
     * @param onlyTask {@link OnlyTask}
     */
    void deleteTask(OnlyTask onlyTask);
}
