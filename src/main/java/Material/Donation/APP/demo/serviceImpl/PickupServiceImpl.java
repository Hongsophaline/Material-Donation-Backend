package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.dto.response.PickupResponse;
import Material.Donation.APP.demo.entity.*;
import Material.Donation.APP.demo.repository.*;
import Material.Donation.APP.demo.service.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PickupServiceImpl implements PickupService {

    private final PickupRepository pickupRepository;
    private final RequestRepository requestRepository;
    private final DonationRepository donationRepository;

    @Override
    @Transactional
    public PickupResponse schedulePickup(UUID requestId, String donorEmail, LocalDateTime scheduledAt, String address) {
        DonationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Security: Only the donor can schedule
        if (!request.getDonation().getDonor().getEmail().equalsIgnoreCase(donorEmail)) {
            throw new RuntimeException("Unauthorized: Only the donor can schedule a pickup");
        }

        if (!"approved".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("Request must be approved before scheduling");
        }

        Pickup pickup = Pickup.builder()
                .request(request)
                .scheduledAt(scheduledAt)
                .pickupAddress(address)
                .status("scheduled")
                .build();

        // Update Donation to reserved
        request.getDonation().setStatus("reserved");
        donationRepository.save(request.getDonation());

        return mapToResponse(pickupRepository.save(pickup));
    }

    @Override
    @Transactional
    public PickupResponse updateStatus(UUID pickupId, String email, String status) {
        Pickup pickup = pickupRepository.findById(pickupId)
                .orElseThrow(() -> new RuntimeException("Pickup not found"));

        // Update Logic
        pickup.setStatus(status);
        if ("completed".equalsIgnoreCase(status)) {
            pickup.setCompletedAt(LocalDateTime.now());
            
            // Finalize the donation
            Donation donation = pickup.getRequest().getDonation();
            donation.setStatus("collected");
            donationRepository.save(donation);
        }

        return mapToResponse(pickupRepository.save(pickup));
    }

    @Override
    public PickupResponse getByRequestId(UUID requestId) {
        return pickupRepository.findByRequestId(requestId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Pickup not found for this request"));
    }

    private PickupResponse mapToResponse(Pickup pickup) {
        DonationRequest req = pickup.getRequest();
        return PickupResponse.builder()
                .id(pickup.getId())
                .requestId(req.getId())
                .donationTitle(req.getDonation().getTitle())
                .requesterName(req.getRequester().getFullName())
                .donorName(req.getDonation().getDonor().getFullName())
                .scheduledAt(pickup.getScheduledAt())
                .pickupAddress(pickup.getPickupAddress())
                .status(pickup.getStatus())
                .completedAt(pickup.getCompletedAt())
                .build();
    }

    @Override
    public Object schedule(UUID requestId, LocalDateTime scheduledAt, String address) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'schedule'");
    }
}