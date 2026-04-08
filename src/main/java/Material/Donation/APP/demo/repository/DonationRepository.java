package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonationRepository extends JpaRepository<Donation, UUID> {
    
    // Custom query to filter by category ID
    // If categoryId is NULL (User selects "All"), the filter is ignored
    @Query("SELECT d FROM Donation d WHERE " +
           "(:categoryId IS NULL OR d.category.id = :categoryId) " +
           "AND d.status = 'available'")
    List<Donation> findByCategoryId(@Param("categoryId") UUID categoryId);

    List<Donation> findByDonorId(UUID donorId);

    @Query("SELECT d FROM Donation d WHERE " +
           "(:keyword IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:categoryId IS NULL OR d.category.id = :categoryId)")
    List<Donation> searchDonations(@Param("keyword") String keyword, @Param("categoryId") UUID categoryId);
}