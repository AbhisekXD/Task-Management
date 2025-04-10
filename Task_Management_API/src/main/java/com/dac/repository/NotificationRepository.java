package com.dac.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dac.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
	List<Notification> findByUserId_IdAndIsReadFalse(UUID userId);
    List<Notification> findByUserId_Id(UUID userId);
}
