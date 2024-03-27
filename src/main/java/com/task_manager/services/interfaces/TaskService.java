package com.task_manager.services.interfaces;

import com.task_manager.domain.NewTask;
import com.task_manager.domain.Task;

public interface TaskService {

    /**
     * Create a new task
     * @param newTask {@link NewTask}
     * @return Task
     */
    Task createTask(NewTask newTask);
}
