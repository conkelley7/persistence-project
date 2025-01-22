package com.kelley.lsd.persistence.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	/*
	 * When we work with ORM it is suggested that we implement equals() and hashcode() methods using
	 * the business ('natural') key fields because these are the properties that would uniquely identify
	 * the instance in the real world.
	 * The @Id id above is only generated when persisted in the dataabase. Before persistence, it is null.
	 * If we used this, then instances might be considered equal when they are clearly not. 
	 * For Tasks, we will use a random UUID, and we can mark it with the @NaturalId annotation. 
	 * Spring Data JPA doesn't provide functionality for this annotation. It is just being used here
	 * for readability.
	 */
	@NaturalId
	@Column(unique = true, nullable = false, updatable = false)
	private String uuid = UUID.randomUUID().toString();
	
	private String name;
	
	private String description;
	
	private LocalDate dueDate;
	
	private TaskStatus status;
	
	/*
	 * Other side of relationship with campaign.
	 * Many tasks can be associated with a single campaign.
	 */
	@ManyToOne(optional=false)
	private Campaign campaign;
	
	/*
	 * In this application, the Task-Worker(assignee) relationship is unidirectional.
	 * Worker entity instances do not contain a Set of Tasks instances, unlike Campaign instances.
	 * Based on the domain/business rules defined for this application, this is the best approach.
	 */
	@ManyToOne
	private Worker assignee;
	
	public Task() {}
	
	public Task(String name, String description, LocalDate dueDate, TaskStatus status, Campaign campaign) {
		super();
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
		this.campaign = campaign;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		// Checks if obj is a Task instance. If so, assigns to 'other', otherwise returns false.
		if (!(obj instanceof Task other)) return false;
		
		return Objects.equals(this.getUuid(), other.getUuid());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getUuid());
	}
	
	
	@Override
    public String toString() {
        return "Task [id=" + id + ", name=" + name + ", description=" + description + ", dueDate=" + dueDate
            + ", status=" + status + "]";
    }
	
	public Long getId() {
		return id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Worker getAssignee() {
		return assignee;
	}

	public void setAssignee(Worker assignee) {
		this.assignee = assignee;
	}
	
	
}
