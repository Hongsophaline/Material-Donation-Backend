package Material.Donation.APP.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Material.Donation.APP.demo.entity.Notification;
import Material.Donation.APP.demo.entity.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    // Fetch notifications by user and recipient type
    List<Notification> findByUserAndRecipientTypeOrderByCreatedAtDesc(User user, String recipientType);

}