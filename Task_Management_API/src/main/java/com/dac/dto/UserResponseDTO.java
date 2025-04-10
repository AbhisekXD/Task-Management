package com.dac.dto;

import java.util.UUID;

public class UserResponseDTO {
    
    private UUID id;
    private String userName;
    private String role;

    public UserResponseDTO() {}

    public UserResponseDTO(UUID id, String userName) {
        super();
        this.id = id;
        this.userName = userName;
    }
    
    public UserResponseDTO(UUID id, String userName, String role) {
        super();
        this.id = id;
        this.userName = userName;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
