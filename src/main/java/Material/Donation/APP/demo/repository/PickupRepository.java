package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, UUID> {
    Optional<Pickup> findByRequestId(UUID requestId);
}