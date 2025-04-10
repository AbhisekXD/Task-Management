package com.dac.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dac.dto.NotificationsResponse;
import com.dac.service.NotificationService;
import com.dac.util.SecurityUtils;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<NotificationsResponse> getUnreadNotifications() {

		UUID userId = SecurityUtils.getLoggedInUserId();

		NotificationsResponse response = notificationService.getUnreadNotifications(userId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<NotificationsResponse> getAllNotifications() {

		UUID userId = SecurityUtils.getLoggedInUserId();

		NotificationsResponse notifications = notificationService.getAllNotifications(userId);
		return ResponseEntity.ok(notifications);
	}

	@PutMapping("/{id}/read")
	public ResponseEntity<Void> markNotificationAsRead(@PathVariable UUID id) {
		notificationService.markAsRead(id);
		return ResponseEntity.ok().build();
	}

}
