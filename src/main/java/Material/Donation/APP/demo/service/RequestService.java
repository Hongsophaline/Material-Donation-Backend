package Material.Donation.APP.demo.service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import Material.Donation.APP.demo.dto.request.CreateRequest;
import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.DonationRequest;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.DonationRepository;
import Material.Donation.APP.demo.repository.RequestRepository;
import Material.Donation.APP.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final DonationRepository donationRepository;
    private final RequestRepository requestRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public void createRequest(CreateRequest dto, UUID requesterId) {
        Donation donation = donationRepository.findById(dto.getDonationId())
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        if (donation.getDonor().getId().equals(requesterId)) {
            throw new RuntimeException("You cannot request your own donation!");
        }

        DonationRequest request = DonationRequest.builder()
                .donation(donation)
                .requester(requester)
                .status("PENDING")
                .message(dto.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        requestRepository.save(request);

        // Notify donor
        notificationService.createNotification(
                donation.getDonor().getId(),
                "DONOR",
                "REQUEST",
                "New Request",
                requester.getFullName() + " requested: " + donation.getTitle()
        );

        // Notify requester
        notificationService.createNotification(
                requester.getId(),
                "USER",
                "REQUEST_SENT",
                "Request Sent",
                "You requested: " + donation.getTitle()
        );
    }
}