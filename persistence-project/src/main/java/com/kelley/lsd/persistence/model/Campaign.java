package com.kelley.lsd.persistence.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Campaign {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * See Task class for notes on Natural ID.
	 * Code will be the Campaign's business key.
	 */
	@NaturalId
	@Column(unique = true, nullable = false, updatable = false)
	private String code;
	
	private String name;
	
	private String description;
	
	/*
	 * Adding relationship to Task entity using @OneToMany annotation:
	 * 1) Tasks should have a "campaign" field, which we are mapping to.
	 * 2) If a Task is removed from the Campaign's list, that Task is removed from the database.
	 * 3) Task entities are loaded immediately, regardless of whether they are currently needed.
	 * 4) If a Campaign is persisted or removed, all associated Tasks are also persisted or removed.
	 */
	@OneToMany(mappedBy="campaign", orphanRemoval=true, fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Task> tasks = new HashSet<>();
	
	public Campaign() {}
	
	public Campaign(String code, String name, String description, Set<Task> tasks) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.tasks = tasks;
	}
	
	public Campaign(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Campaign [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description + ", tasks="
				+ tasks + "]";
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.getCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Campaign other)) return false;
		
		return Objects.equals(this.getCode(), other.getCode());
	}
	
}
