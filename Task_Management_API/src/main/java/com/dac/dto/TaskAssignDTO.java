package com.dac.dto;

import java.util.UUID;

import com.dac.model.enums.Status;

public class TaskAssignDTO {
    
    private UUID id;
    private String title;
    private String description;
    private Status status;
    private UserResponseDTO assignedTo;

    
    public TaskAssignDTO(UUID id, String title, String description, Status status, UserResponseDTO assignedTo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedTo = assignedTo;
    }

   
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

    public UserResponseDTO getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserResponseDTO assignedTo) {
        this.assignedTo = assignedTo;
    }
}
