package Material.Donation.APP.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "donations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Link to User (donor)
    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor;

    // Link to Donation Images
    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationImage> images;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // 🔥 Proper Category relationship
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String condition;

    @Builder.Default
    private String status = "available";

    private String address;
    private Double latitude;
    private Double longitude;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Optional: approval status
    @Builder.Default
    private String approvalStatus = "pending";
}