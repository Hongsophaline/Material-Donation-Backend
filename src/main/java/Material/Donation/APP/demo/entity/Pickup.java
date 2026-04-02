package Material.Donation.APP.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pickups")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private DonationRequest request; // Your Request Entity

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(name = "pickup_address", nullable = false)
    private String pickupAddress;

    @Column(nullable = false)
    private String status; // scheduled, in_progress, completed, cancelled

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}