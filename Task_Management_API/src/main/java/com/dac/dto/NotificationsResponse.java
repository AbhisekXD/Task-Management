package com.dac.dto;

import java.util.List;

public class NotificationsResponse {
	
    private long count;
    private List<NotificationDTO> notifications;

    public NotificationsResponse(long count, List<NotificationDTO> notifications) {
        this.count = count;
        this.notifications = notifications;
    }

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<NotificationDTO> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<NotificationDTO> notifications) {
		this.notifications = notifications;
	}

}
