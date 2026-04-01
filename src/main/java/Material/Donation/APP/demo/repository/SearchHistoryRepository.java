package Material.Donation.APP.demo.repository;

import Material.Donation.APP.demo.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, UUID> { }