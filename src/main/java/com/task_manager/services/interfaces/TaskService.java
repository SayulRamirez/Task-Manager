package com.task_manager.services.interfaces;

import com.task_manager.domain.NewTask;
import com.task_manager.domain.OnlyTask;
import com.task_manager.domain.Task;
import com.task_manager.domain.UpdateTask;

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
     * Find all task by id author
     * @param id {@link Long}
     * @return Things to do
     */
    List<Task> findAll(Long id);

    /**
     * Find one task by id author
     * @param id {@link Long}
     * @return Task
     */
    Task findByIdAuthor(OnlyTask onlyTask);
}
