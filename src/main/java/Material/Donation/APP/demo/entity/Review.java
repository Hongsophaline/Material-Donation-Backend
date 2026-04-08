package Material.Donation.APP.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID reviewerId; // FK -> USERS.id
    private UUID reviewedId; // FK -> USERS.id
    private UUID donationId; // FK -> DONATIONS.id

    private int rating;
    private String comment;

    private LocalDateTime createdAt;
}