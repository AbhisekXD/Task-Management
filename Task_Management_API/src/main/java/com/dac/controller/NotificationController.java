package com.dac.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dac.dto.NotificationDTO;
import com.dac.dto.UnreadNotificationsResponse;
import com.dac.service.NotificationService;
import com.dac.util.SecurityUtils;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping
	public ResponseEntity<UnreadNotificationsResponse> getUnreadNotifications() {

		UUID userId = SecurityUtils.getLoggedInUserId();

		UnreadNotificationsResponse response = notificationService.getUnreadNotifications(userId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/all")
	public ResponseEntity<List<NotificationDTO>> getAllNotifications() {

		UUID userId = SecurityUtils.getLoggedInUserId();

		List<NotificationDTO> notifications = notificationService.getAllNotifications(userId);
		return ResponseEntity.ok(notifications);
	}

	@PutMapping("/{id}/read")
	public ResponseEntity<Void> markNotificationAsRead(@PathVariable UUID id) {
		notificationService.markAsRead(id);
		return ResponseEntity.ok().build();
	}

}
