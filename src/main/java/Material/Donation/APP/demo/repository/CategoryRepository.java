package Material.Donation.APP.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import Material.Donation.APP.demo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}