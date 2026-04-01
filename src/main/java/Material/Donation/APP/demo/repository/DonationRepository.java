package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonationRepository extends JpaRepository<Donation, UUID> {

    // Existing method
    List<Donation> findByDonorId(UUID donorId);

    // 🔹 Search donations by keyword and optional category
    @Query("SELECT d FROM Donation d " +
           "WHERE (LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(d.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:category IS NULL OR d.category = :category) " +
           "AND d.approvalStatus = 'approved' " +
           "AND d.status = 'available'")
    List<Donation> searchDonations(@Param("keyword") String keyword,
                                   @Param("category") Category category);
}