package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.dto.request.UpdateDonationRequest;
import Material.Donation.APP.demo.dto.response.DonationResponse;
import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.DonationImage;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.DonationRepository;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Material.Donation.APP.demo.repository.DonationImageRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    @SuppressWarnings("unused")
    private final DonationImageRepository DonationImageRepository;
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
   @Override
public DonationResponse updateDonation(UUID donationId, String email, UpdateDonationRequest request) {
    // 1. Find the donation
    Donation donation = donationRepository.findById(donationId)
            .orElseThrow(() -> new RuntimeException("Donation not found"));

    // 2. Security Check: Is the person updating it the actual owner?
    if (!donation.getDonor().getEmail().equals(email)) {
        throw new RuntimeException("You do not have permission to update this donation");
    }

    // 3. Update fields if provided
    if (request.getTitle() != null) donation.setTitle(request.getTitle());
    if (request.getDescription() != null) donation.setDescription(request.getDescription());
    if (request.getCategoryId() != null) donation.setCategoryId(request.getCategoryId());
    if (request.getCondition() != null) donation.setCondition(request.getCondition());
    if (request.getStatus() != null) donation.setStatus(request.getStatus());
    if (request.getAddress() != null) donation.setAddress(request.getAddress());
    if (request.getLatitude() != null) donation.setLatitude(request.getLatitude());
    if (request.getLongitude() != null) donation.setLongitude(request.getLongitude());

    // 4. Save and Map back to Response DTO
    return mapToResponse(donationRepository.save(donation));
}
@Override
public void deleteDonation(UUID donationId, String email) {
    // 1. Find the donation
    Donation donation = donationRepository.findById(donationId)
            .orElseThrow(() -> new RuntimeException("Donation not found")); 

    // 2. Security Check: Is the person deleting it the actual owner?
    if (!donation.getDonor().getEmail().equals(email)) {
        throw new RuntimeException("You do not have permission to delete this donation");
    }

    // 3. Delete the donation
    donationRepository.delete(donation);
}
// Make sure you have this repository injected at the top
private final DonationImageRepository donationImageRepository;

@Override
public void addDonationImage(UUID donationId, String email, String imageUrl) {
    // 1. Find the donation
    Donation donation = donationRepository.findById(donationId)
            .orElseThrow(() -> new RuntimeException("Donation not found"));

    // 2. Permission Check
    if (!donation.getDonor().getEmail().equalsIgnoreCase(email)) {
        throw new RuntimeException("You do not have permission to add images to this donation");
    }

    // 3. Create and Save Image
    DonationImage img = DonationImage.builder()
            .donation(donation)
            .imageUrl(imageUrl)
            .sortOrder(0)
            .build();

    donationImageRepository.save(img);
}
}