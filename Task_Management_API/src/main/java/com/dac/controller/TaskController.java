package com.dac.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dac.config.ApiResponse;
import com.dac.dto.TaskAssignDTO;
import com.dac.dto.TaskCreateDTO;
import com.dac.dto.TaskDTO;
import com.dac.model.Task;
import com.dac.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Task>> createTask(@Valid @RequestBody TaskCreateDTO taskCreateDTO) {
        try {
            Task createdTask = taskService.createTask(taskCreateDTO);
            return ResponseEntity.ok(ApiResponse.success("Task created successfully.", createdTask));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("Failed to create task: " + e.getMessage()));
        }
    }

    @PostMapping("/{taskId}/assign/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TaskAssignDTO>> assignTask(@PathVariable UUID taskId, @PathVariable UUID userId) {
        try {
            TaskAssignDTO assignedTask = taskService.assignTaskToUser(taskId, userId);
            return ResponseEntity.ok(ApiResponse.success("Task assigned successfully.", assignedTask));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("An unexpected error occurred."));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getTasksAssignedToLoggedInUser() {
        try {
            List<TaskDTO> tasks = taskService.getTasksAssignedToLoggedInUser();
            return ResponseEntity.ok(ApiResponse.success("Tasks fetched successfully.", tasks));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("Failed to fetch tasks: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTaskStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        try {
            TaskDTO updatedTask = taskService.updateTaskStatus(id, status);
            return ResponseEntity.ok(ApiResponse.success("Task status updated successfully.", updatedTask));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("An unexpected error occurred."));
        }
    }
}
