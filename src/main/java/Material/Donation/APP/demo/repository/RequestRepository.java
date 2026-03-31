package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.DonationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<DonationRequest, UUID> {
    // This allows the "getRequestsByRequester" method to work
    List<DonationRequest> findByRequesterId(UUID requesterId);
}