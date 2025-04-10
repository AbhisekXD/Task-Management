package com.dac.dto;

import java.util.UUID;

public class TaskDTO {
    private UUID id;
    private String title;
    private String description;
    private String status;
    //private UserResponseDTO assignedTo;

    
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public UserResponseDTO getAssignedTo() {
//        return assignedTo;
//    }
//
//    public void setAssignedTo(UserResponseDTO assignedTo) {
//        this.assignedTo = assignedTo;
//    }
}
