package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.dto.response.DonationResponse;
import Material.Donation.APP.demo.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationResponse> create(Principal principal, @RequestBody DonationRequest request) {
        return ResponseEntity.ok(donationService.createDonation(principal.getName(), request));
    }

    @GetMapping
    public ResponseEntity<List<DonationResponse>> getAll() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @GetMapping("/my")
    public ResponseEntity<List<DonationResponse>> getMyDonations(Principal principal) {
        return ResponseEntity.ok(donationService.getDonationsByUser(principal.getName()));
    }
}