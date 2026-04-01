package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.dto.response.DonationResponse;
import Material.Donation.APP.demo.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final DonationService donationService;

    @GetMapping
    public ResponseEntity<List<DonationResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) UUID categoryId) {
        
        return ResponseEntity.ok(donationService.searchDonations(keyword, categoryId));
    }
}