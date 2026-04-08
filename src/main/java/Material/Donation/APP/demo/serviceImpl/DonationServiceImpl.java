package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.dto.request.UpdateDonationRequest;
import Material.Donation.APP.demo.dto.response.DonationResponse;
import Material.Donation.APP.demo.entity.Category;
import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.DonationImage;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.DonationRepository;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.repository.DonationImageRepository;
import Material.Donation.APP.demo.repository.CategoryRepository;
import Material.Donation.APP.demo.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final DonationImageRepository donationImageRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public DonationResponse createDonation(String email, DonationRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Donation donation = Donation.builder()
                .donor(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(category)
                .condition(request.getCondition())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .status("available")
                .build();

        return mapToResponse(donationRepository.save(donation));
    }

    @Override
    public List<DonationResponse> getAllDonations(UUID categoryId) {
        // Updated to use the filtered repository method
        return donationRepository.findByCategoryId(categoryId).stream()
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

    @Override
    @Transactional
    public DonationResponse updateDonation(UUID donationId, String email, UpdateDonationRequest request) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        if (!donation.getDonor().getEmail().equalsIgnoreCase(email)) {
            throw new RuntimeException("You do not have permission to update this donation");
        }

        if (request.getTitle() != null) donation.setTitle(request.getTitle());
        if (request.getDescription() != null) donation.setDescription(request.getDescription());
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            donation.setCategory(category);
        }
        if (request.getCondition() != null) donation.setCondition(request.getCondition());
        if (request.getStatus() != null) donation.setStatus(request.getStatus());
        if (request.getAddress() != null) donation.setAddress(request.getAddress());

        return mapToResponse(donationRepository.save(donation));
    }

    @Override
    @Transactional
    public void deleteDonation(UUID donationId, String email) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        if (!donation.getDonor().getEmail().equalsIgnoreCase(email)) {
            throw new RuntimeException("You do not have permission to delete this donation");
        }

        donationRepository.delete(donation);
    }

    @Override
    @Transactional
    public void addDonationImage(UUID donationId, String email, String imageUrl) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        if (!donation.getDonor().getEmail().equalsIgnoreCase(email)) {
            throw new RuntimeException("Permission denied");
        }

        DonationImage img = DonationImage.builder()
                .donation(donation)
                .imageUrl(imageUrl)
                .sortOrder(0)
                .build();

        donationImageRepository.save(img);
    }

    private DonationResponse mapToResponse(Donation donation) {
        return DonationResponse.builder()
                .id(donation.getId())
                .title(donation.getTitle())
                .description(donation.getDescription())
                .categoryId(donation.getCategory() != null ? donation.getCategory().getId() : null)
                .condition(donation.getCondition())
                .status(donation.getStatus())
                .address(donation.getAddress())
                .donorName(donation.getDonor() != null ? donation.getDonor().getFullName() : "Unknown")
                .imageUrls(donation.getImages() != null ? 
                    donation.getImages().stream()
                        .map(DonationImage::getImageUrl)
                        .collect(Collectors.toList()) 
                    : List.of())
                .createdAt(donation.getCreatedAt())
                .build();
    }

    @Override
    public Object searchDonations(String keyword, UUID categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchDonations'");
    }
}