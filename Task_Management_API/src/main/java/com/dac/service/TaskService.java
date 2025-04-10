package com.dac.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.dto.TaskAssignDTO;
import com.dac.dto.TaskCreateDTO;
import com.dac.dto.TaskDTO;
import com.dac.dto.UserResponseDTO;
import com.dac.model.Task;
import com.dac.model.User;
import com.dac.model.enums.Status;
import com.dac.repository.TaskRepository;
import com.dac.repository.UserRepository;
import com.dac.util.SecurityUtils;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ModelMapper modelMapper;

    
    
    public Task createTask(TaskCreateDTO taskCreateDTO) {
        Task task = modelMapper.map(taskCreateDTO, Task.class);
        return taskRepository.save(task);
    }
    
    
    public TaskAssignDTO assignTaskToUser(UUID taskId, UUID userId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

//        if (task.getAssignedTo() != null) {
//            throw new IllegalArgumentException("Task is already assigned to user: " 
//                    + task.getAssignedTo().getUserName());
//        }	
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        task.setAssignedTo(user);

        Task updatedTask = taskRepository.save(task);
        
        String message = "You have been assigned the task: " + task.getTitle();
        notificationService.sendNotification(user.getId(), message);

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getUserName());
        
        return new TaskAssignDTO(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getStatus(),
                userResponseDTO
        );
    }

    
    public List<TaskDTO> getTasksAssignedToLoggedInUser() {
        
         UUID loggedInUserId = SecurityUtils.getLoggedInUserId();

        User user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("User not found with user Id: " + loggedInUserId));
        
        List<Task> tasks = taskRepository.findByAssignedTo(user);

        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }
   
    
    public TaskDTO updateTaskStatus(UUID id, String status) {
        UUID userId = SecurityUtils.getLoggedInUserId();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (task.getAssignedTo() == null || !task.getAssignedTo().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not assigned to this task.");
        }

        try {
            Status taskStatus = Status.valueOf(status.toUpperCase());
            task.setStatus(taskStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        Task updatedTask = taskRepository.save(task);

        return modelMapper.map(updatedTask, TaskDTO.class);
    }

}
