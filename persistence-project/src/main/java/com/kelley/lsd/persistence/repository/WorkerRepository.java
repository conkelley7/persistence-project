package com.kelley.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.kelley.lsd.persistence.model.Worker;

public interface WorkerRepository extends CrudRepository<Worker, Long> {
}
