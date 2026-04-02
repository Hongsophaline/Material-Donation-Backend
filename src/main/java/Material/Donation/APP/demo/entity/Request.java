package Material.Donation.APP.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester; // instead of UUID requesterId

    private String status; // pending, approved, rejected, cancelled

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime createdAt;
}