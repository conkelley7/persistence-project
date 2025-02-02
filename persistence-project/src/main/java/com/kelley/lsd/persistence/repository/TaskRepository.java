package com.kelley.lsd.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kelley.lsd.persistence.model.Task;
import com.kelley.lsd.persistence.model.TaskStatus;

public interface TaskRepository extends CrudRepository<Task, Long> {
	// Find all tasks with a due date after the given date.
	List<Task> findByDueDateGreaterThan(LocalDate dueDate);
	
	/*
	 * Some other comparison operators include:
	 * LessThan
	 * LessThanEqual
	 * GreaterThanEqual
	 * 
	 * Another option for the above due date query:
	 * findByDueDateAfter(LocalDate dueDate);
	 *
	 * Before is another comparison operator.
	 */
	
	/*
	 * And/Or logical keywords can be used to combine multiple conditions.
	 * 
	 * Example:
	 * Find all Tasks which have passed due date (before dueDate argument), and still have status TO_DO
	 */
	List<Task>findByDueDateBeforeAndStatusEquals(LocalDate dueDate, TaskStatus status);
	
	/*
	 * Querying on Nested Properties:
	 * 
	 * We can traverse to a nested property through the parent.
	 * In the query below, AssigneeFirstName is interpreted as assignee.firstName
	 */
	List<Task> findByAssigneeFirstName(String name);
	
	/*
	 * Custom query to get the number of Tasks that are due each year.
	 * 
	 * Use GROUP BY clause to group tasks by the year in which they're due
	 * Retreives a List of List objects
	 * 
	 * The outer list represents a collection of result rows.
	 * Inner list contains two values - one for grouped year and the other for the corresponding number of tasks due that year.
	 */
	@Query("select count(*), year(t.dueDate) from Task t group by year(t.dueDate)")
	List<List<Integer>> countByDueYear();
	
	
	
	
	

}
