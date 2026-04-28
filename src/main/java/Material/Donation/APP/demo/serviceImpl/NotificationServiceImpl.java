package Material.Donation.APP.demo.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import Material.Donation.APP.demo.entity.Notification;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.NotificationRepository;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.service.NotificationService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository; 
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void createNotification(UUID userId, String role, String type, String title, String message) {
        Notification notification = Notification.builder()
            .userId(userId)
            .role(role)
            .type(type)
            .title(title)
            .message(message)
            .isRead(false) // <--- Set this explicitly
            .createdAt(LocalDateTime.now())
            .build();

        Notification saved = notificationRepository.save(notification);

        // Real-time push
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                saved
        );
    }

    @Override
    public List<Notification> getMyNotifications(UUID userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    @Transactional
    public void markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public long countUnreadNotifications(UUID userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    @Override
    public void markAllAsRead(UUID userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }
}