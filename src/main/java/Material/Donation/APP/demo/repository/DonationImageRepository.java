package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.DonationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface DonationImageRepository extends JpaRepository<DonationImage, UUID> {
    List<DonationImage> findByDonationId(UUID donationId);
}