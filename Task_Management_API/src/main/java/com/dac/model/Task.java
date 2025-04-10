package com.dac.model;

import java.util.UUID;

import com.dac.model.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "task_id")
	private UUID id;
	
	@Column(name = "task_title",nullable = false)
	private String title;
	
	@Column(name = "task_description",columnDefinition = "text")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "task_status")
	private Status status;
	
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "assigned_to")
	private User assignedTo;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Task() {}

	public Task(UUID id, String title, String description, Status status, User assignedTo) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.assignedTo = assignedTo;
	}
	
}
