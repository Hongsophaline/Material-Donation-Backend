package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // This will find the user by their Identifier (Phone or Email)
    Optional<User> findByEmail(String identifier);
}