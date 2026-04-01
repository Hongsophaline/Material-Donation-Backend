package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.entity.Category;
import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.SearchHistory;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.DonationRepository;
import Material.Donation.APP.demo.repository.SearchHistoryRepository;
import Material.Donation.APP.demo.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final DonationRepository donationRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    @Override
    public List<Donation> searchDonations(User user, String keyword, Category category) {

        // Save search history
        SearchHistory history = SearchHistory.builder()
                .user(user)
                .keyword(keyword)
                .category(category)
                .build();
        searchHistoryRepository.save(history);

        // Return matching donations
        return donationRepository.searchDonations(keyword, category);
    }
}