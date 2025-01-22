package com.kelley.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.kelley.lsd.persistence.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
