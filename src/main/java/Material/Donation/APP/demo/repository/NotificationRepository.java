package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.Notification;
import Material.Donation.APP.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    // Get all notifications for a user, newest on top
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    
    // Count unread notifications for a badge in the UI
    long countByUserAndIsReadFalse(User user);
    List<Notification> findByUserAndIsReadFalse(User user);
}