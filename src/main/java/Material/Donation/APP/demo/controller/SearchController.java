package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.entity.Category;
import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.CategoryRepository;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Donation> searchDonations(
            @RequestParam UUID userId,
            @RequestParam String keyword,
            @RequestParam(required = false) UUID categoryId
    ) {
        // Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch category (optional)
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        return searchService.searchDonations(user, keyword, category);
    }
}