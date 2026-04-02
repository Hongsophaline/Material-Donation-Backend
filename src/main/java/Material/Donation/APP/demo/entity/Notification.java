package Material.Donation.APP.demo.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String type; // e.g., "donation_request", "donation_approved"
    private String title;
    private String message;

    private boolean isRead;

    // Role of the notification recipient: "REQUESTER" or "DONOR"
    private String recipientType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // links to the user who receives the notification

    @CreationTimestamp
    private LocalDateTime createdAt;
}