package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.entity.Notification;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.NotificationRepository;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createNotification(UUID userId, String role, String type, String title, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .recipientType(role != null ? role.toUpperCase() : "GENERAL")
                .type(type)
                .title(title)
                .message(message)
                .isRead(false) // Initial state is always unread
                .build();

        notificationRepository.save(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> getMyNotifications(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // This ensures new notifications appear at the top of your UI list
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
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
    @Transactional
    public void markAllAsRead(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Fetches only notifications that are currently 'false'
        List<Notification> unread = notificationRepository.findByUserAndIsReadFalse(user);
        
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }
}