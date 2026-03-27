package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.DonationRepository;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.service.DonationService;
import lombok.RequiredArgsConstructor; // 👈 Add this import
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 👈 This generates the constructor for final fields
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    @Override
    public Donation createDonation(String userEmail, DonationRequest request) {
        // 1. Get the user from the database
        User donor = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Build the Donation entity
        Donation donation = Donation.builder()
                .donor(donor)
                .title(request.getTitle())
                .description(request.getDescription())
                .categoryId(request.getCategoryId())
                .condition(request.getCondition())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .status("available")
                .build();

        // 3. Save and return
        return donationRepository.save(donation);
    }
}