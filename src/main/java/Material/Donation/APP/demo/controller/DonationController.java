package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.dto.request.UpdateDonationRequest;
import Material.Donation.APP.demo.dto.response.DonationResponse;
import Material.Donation.APP.demo.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/donations")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    // GET ALL DONATIONS
    @GetMapping
    public ResponseEntity<List<DonationResponse>> getAll(
            @RequestParam(required = false) UUID categoryId) {

        return ResponseEntity.ok(donationService.getAllDonations(categoryId));
    }

    // GET DONATION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(donationService.getDonationById(id));
    }

    // CREATE DONATION - FIXED
    @PostMapping
    public ResponseEntity<DonationResponse> create(
            Authentication authentication,     // Changed from Principal
            @RequestBody DonationRequest request) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();

        return ResponseEntity.ok(
                donationService.createDonation(username, request)
        );
    }

    // GET MY DONATIONS
    @GetMapping("/my")
    public ResponseEntity<List<DonationResponse>> getMyDonations(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        return ResponseEntity.ok(
                donationService.getDonationsByUser(username)
        );
    }

    // UPDATE DONATION
    @PutMapping("/{id}")
    public ResponseEntity<DonationResponse> update(
            @PathVariable UUID id,
            Authentication authentication,
            @RequestBody UpdateDonationRequest request) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        return ResponseEntity.ok(
                donationService.updateDonation(id, username, request)
        );
    }

    // DELETE DONATION
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            Authentication authentication,
            @PathVariable UUID id) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        donationService.deleteDonation(id, username);
        return ResponseEntity.noContent().build();
    }

    // ADD IMAGE TO DONATION
    @PostMapping("/{id}/images")
    public ResponseEntity<String> addImage(
            @PathVariable UUID id,
            @RequestParam String imageUrl,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        donationService.addDonationImage(id, username, imageUrl);
        return ResponseEntity.ok("Image added successfully");
    }
}