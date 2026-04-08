package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByReviewedId(UUID reviewedId);
    List<Review> findByDonationId(UUID donationId);
}