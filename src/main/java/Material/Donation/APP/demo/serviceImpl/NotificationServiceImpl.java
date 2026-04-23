package Material.Donation.APP.demo.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import Material.Donation.APP.demo.entity.Notification;
import Material.Donation.APP.demo.repository.NotificationRepository;
import Material.Donation.APP.demo.service.NotificationService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // =========================
    // 🔔 CREATE NOTIFICATION + REAL TIME
    // =========================
    @Override
    public void createNotification(UUID userId,
                                   String role,
                                   String type,
                                   String title,
                                   String message) {

        Notification notification = Notification.builder()
                .userId(userId)
                .role(role)
                .type(type)
                .title(title)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);

        // 🔥 SEND REAL-TIME NOTIFICATION
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                saved
        );
    }

    // =========================
    // 📩 GET NOTIFICATIONS
    // =========================
    @Override
    public List<Notification> getMyNotifications(UUID userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // =========================
    // ✅ MARK ONE AS READ
    // =========================
    @Override
    public void markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    // =========================
    // ✅ MARK ALL AS READ
    // =========================
    @Override
    public void markAllAsRead(UUID userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);

        for (Notification n : notifications) {
            n.setRead(true);
        }

        notificationRepository.saveAll(notifications);
    }

    // =========================
    // 🔴 COUNT UNREAD NOTIFICATIONS
    // =========================
    @Override
    public long countUnreadNotifications(UUID userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }
}