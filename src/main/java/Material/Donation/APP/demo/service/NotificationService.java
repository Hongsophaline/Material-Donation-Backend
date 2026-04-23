package Material.Donation.APP.demo.service;

import Material.Donation.APP.demo.entity.Notification;
import java.util.List;
import java.util.UUID;

public interface NotificationService {

    // 🔔 Create + send real-time notification
    void createNotification(UUID userId, String role, String type, String title, String message);

    // 📩 Get all notifications for a user
    List<Notification> getMyNotifications(UUID userId);

    // ✅ Mark one notification as read
    void markAsRead(UUID notificationId);

    // ✅ Mark all notifications as read
    void markAllAsRead(UUID userId);

    // 🆕 OPTIONAL (recommended for UI badge counter)
    long countUnreadNotifications(UUID userId);
}