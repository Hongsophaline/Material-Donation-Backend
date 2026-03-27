package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<Donation> createDonation(Principal principal, @RequestBody DonationRequest request) {
        // principal.getName() is the email from the JWT
        return ResponseEntity.ok(donationService.createDonation(principal.getName(), request));
    }
}