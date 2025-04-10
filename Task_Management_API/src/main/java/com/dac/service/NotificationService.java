package com.dac.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.controller.NotificationSseController;
import com.dac.dto.NotificationDTO;
import com.dac.dto.UnreadNotificationsResponse;
import com.dac.model.Notification;
import com.dac.model.User;
import com.dac.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationSseController notificationSseController;
    
    public void sendNotification(UUID userId, String message) {
    	
        User user = new User();
        user.setId(userId);
        Notification notification = new Notification(message, user);
        
        notificationRepository.save(notification);

        Map<String, Object> payload = new HashMap<>();
        payload.put("notificationId", notification.getId());
        payload.put("message", message);

        // SSE (Server Sent Event) .
        notificationSseController.sendNotification(userId, payload);

        System.err.println("Attempting to send SSE message:");
        System.err.println("User ID: " + userId);
        System.err.println("Payload: " + payload);
        
        
//        web socket
        
//        messagingTemplate.convertAndSendToUser(userId.toString(), "/queue/notifications", payload);
//        
//        System.err.println("Attempting to send WebSocket message:");
//        System.err.println("Destination: /queue/notifications");
//        System.err.println("User ID: " + userId);
//        System.err.println("Payload: " + payload);
        
    }
    
    public UnreadNotificationsResponse getUnreadNotifications(UUID userId) {
        List<NotificationDTO> dtoList = notificationRepository.findByUserId_IdAndIsReadFalse(userId)
            .stream()
            .map(n -> new NotificationDTO(
                    n.getId(),
                    n.getMessage(),
                    n.getUserId().getId(),
                    n.getTimeStamp(),
                    n.isRead()))
            .toList();

        return new UnreadNotificationsResponse(dtoList.size(), dtoList);
    }
    
    public List<NotificationDTO> getAllNotifications(UUID userId) {
        return notificationRepository.findByUserId_Id(userId)
                .stream()
                .map(n -> new NotificationDTO(
                        n.getId(),
                        n.getMessage(),
                        n.getUserId().getId(),
                        n.getTimeStamp(),
                        n.isRead()))
                .toList();
    }
    
    public void markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
    
}
