package Material.Donation.APP.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "recipient_type")
    private String recipientType;

    private String type;
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String message;

    @Builder.Default
    @Column(name = "read", nullable = false) 
    private boolean isRead = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Helper to ensure 'setRead' works regardless of Lombok naming
    public void setRead(boolean read) {
        this.isRead = read;
    }
}