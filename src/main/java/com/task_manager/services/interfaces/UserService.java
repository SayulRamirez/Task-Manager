package com.task_manager.services.interfaces;

import com.task_manager.domain.Author;
import com.task_manager.domain.NewUser;

public interface UserService {

    /**
     * Create a new user
     * @param newUser {@link NewUser}
     * @return Author
     */
    Author createUser(NewUser newUser);
}
