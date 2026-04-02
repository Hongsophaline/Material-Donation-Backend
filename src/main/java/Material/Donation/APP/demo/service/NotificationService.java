package Material.Donation.APP.demo.service;

import Material.Donation.APP.demo.entity.Notification;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    // 5 parameters to match RequestService calls
    void createNotification(UUID userId, String role, String type, String title, String message);
    
    List<Notification> getMyNotifications(UUID userId);
    
    void markAsRead(UUID notificationId);
    void markAllAsRead(UUID userId);
}