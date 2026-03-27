package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.dto.response.DonationResponse;
import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.DonationRepository;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    @Override
    public DonationResponse createDonation(String email, DonationRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Donation donation = Donation.builder()
                .donor(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .categoryId(request.getCategoryId())
                .condition(request.getCondition())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .status("available")
                .build();

        return mapToResponse(donationRepository.save(donation));
    }

    @Override
    public List<DonationResponse> getAllDonations() {
        return donationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponse> getDonationsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return donationRepository.findByDonorId(user.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private DonationResponse mapToResponse(Donation donation) {
        return DonationResponse.builder()
                .id(donation.getId())
                .title(donation.getTitle())
                .description(donation.getDescription())
                .categoryId(donation.getCategoryId())
                .condition(donation.getCondition())
                .status(donation.getStatus())
                .address(donation.getAddress())
                .latitude(donation.getLatitude())
                .longitude(donation.getLongitude())
                .donorName(donation.getDonor().getFullName())
                .donorEmail(donation.getDonor().getEmail())
                .createdAt(donation.getCreatedAt())
                .build();
    }
}