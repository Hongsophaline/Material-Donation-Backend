package Material.Donation.APP.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Material.Donation.APP.demo.entity.DonationRequest;
import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<DonationRequest, UUID> {

    // Find all requests by requester ID
    List<DonationRequest> findByRequesterId(UUID requesterId);

    // Optional: find all requests for a specific donor
    List<DonationRequest> findByDonationDonorId(UUID donorId);
}