package Material.Donation.APP.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Material.Donation.APP.demo.entity.Notification;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    // 📩 Get all notifications of a user
    List<Notification> findByUserId(UUID userId);

    // 📩 Get notifications sorted by newest first
    List<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId);

    // 🔴 Count unread notifications (useful for badge UI)
    long countByUserIdAndIsReadFalse(UUID userId);
}