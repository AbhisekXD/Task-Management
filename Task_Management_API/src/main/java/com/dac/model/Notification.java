package com.dac.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "notification_id")
	private UUID id;

	@Column(name = "notification_message")
	private String message;

	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "notification_user_id", nullable = false)
	private User userId;

	@Column(name = "notification_is_read")
	private boolean isRead = false;

	@Column(name = "notification_timestamp")//updatable = false
	private LocalDateTime timeStamp;

	@PrePersist
	protected void onCreate() {
		this.timeStamp = LocalDateTime.now();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	// Constructors
	public Notification() {}

	public Notification(String message, User userId) {
		this.message = message;
		this.userId = userId;
	}

	public Notification(UUID id, String message, User userId, boolean isRead, LocalDateTime timeStamp) {
		this.id = id;
		this.message = message;
		this.userId = userId;
		this.isRead = isRead;
		this.timeStamp = timeStamp;
	}
}
