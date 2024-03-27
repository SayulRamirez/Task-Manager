package com.task_manager.repositories;

import com.task_manager.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("""
    select t from TaskEntity t where t.id =:idTask and t.author.id =:idAuthor
    """)
    TaskEntity findTaskByIdAndIdAuthor(Long idTask, Long idAuthor);

}
