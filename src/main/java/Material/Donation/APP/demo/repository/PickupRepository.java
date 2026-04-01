package Material.Donation.APP.demo.repository;

import javax.swing.Popup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupRepository extends JpaRepository<Popup, String> {
}