package Material.Donation.APP.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import Material.Donation.APP.demo.repository.NotificationRepository;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.entity.Notification;
import Material.Donation.APP.demo.entity.User;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // Fetch notifications by user and role (USER or DONOR)
    public List<Notification> getNotifications(UUID userId, String role) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.findByUserAndRecipientTypeOrderByCreatedAtDesc(
                user, role.toUpperCase()
        );
    }

    // Create a new notification for a user/donor
    public void createNotification(UUID userId, String role, String type, String title, String message) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .recipientType(role.toUpperCase())
                .type(type)
                .title(title)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }

    // Mark a notification as read
    public void markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);  // <-- fixed
        notificationRepository.save(notification);
    }
}