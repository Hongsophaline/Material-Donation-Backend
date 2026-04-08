package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface DonationRepository extends JpaRepository<Donation, UUID> {
    
    List<Donation> findByDonorId(UUID donorId);

    @Query("SELECT d FROM Donation d WHERE " +
           "(:categoryId IS NULL OR d.category.id = :categoryId) AND " +
           "(:keyword IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(d.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(d.status = 'available')")
    List<Donation> searchDonations(
            @Param("keyword") String keyword, 
            @Param("categoryId") UUID categoryId);
}