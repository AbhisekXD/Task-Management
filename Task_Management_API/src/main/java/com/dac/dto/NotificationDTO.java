package com.dac.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationDTO {
	
    private UUID id;
    private String message;
    private UUID userId;
    private LocalDateTime timeStamp;
    private boolean isRead;

    public NotificationDTO(UUID id, String message, UUID userId, LocalDateTime timeStamp, boolean isRead) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.isRead = isRead;
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

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
}
