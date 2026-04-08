package Material.Donation.APP.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.DonationRequest;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.dto.request.CreateRequest;
import Material.Donation.APP.demo.dto.response.RequestResponse;
import Material.Donation.APP.demo.repository.DonationRepository;
import Material.Donation.APP.demo.repository.RequestRepository;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.service.DonationRequestService;
import Material.Donation.APP.demo.service.NotificationService;

@Service
@RequiredArgsConstructor
public class DonationRequestServiceImpl implements DonationRequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final DonationRepository donationRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public RequestResponse createRequest(String email, CreateRequest dto) {
        if (dto.getDonationId() == null) {
            throw new RuntimeException("Validation Error: donationId is null");
        }

        User requester = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Donation donation = donationRepository.findById(dto.getDonationId())
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        if (donation.getDonor().getEmail().equalsIgnoreCase(email)) {
            throw new RuntimeException("You cannot request your own donation!");
        }

        DonationRequest request = DonationRequest.builder()
                .donation(donation)
                .requester(requester)
                .message(dto.getMessage())
                .status("pending")
                .createdAt(LocalDateTime.now())
                .build();

        DonationRequest savedRequest = requestRepository.save(request);

        // 🔔 Notify donor (recipient type = DONOR)
        notificationService.createNotification(
                donation.getDonor().getId(),
                "DONOR",
                "REQUEST",
                "New Request",
                requester.getFullName() + " requested your donation: " + donation.getTitle()
        );

        return mapToResponse(savedRequest);
    }

    @Override
    public List<RequestResponse> getRequestsByRequester(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return requestRepository.findByRequesterId(user.getId())
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestResponse> getRequestsForMyDonations(String email) {
        return requestRepository.findAll().stream()
                .filter(req -> req.getDonation().getDonor().getEmail().equalsIgnoreCase(email))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RequestResponse updateRequestStatus(UUID requestId, String currentUserEmail, String status) {
        DonationRequest req = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!req.getDonation().getDonor().getEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new RuntimeException("Unauthorized: You do not own this donation item.");
        }

        req.setStatus(status);

        // Update donation status if approved
        if ("approved".equalsIgnoreCase(status)) {
            Donation donation = req.getDonation();
            donation.setStatus("pending"); // Item is now reserved
            donationRepository.save(donation);
        }

        DonationRequest updatedRequest = requestRepository.save(req);

        // 🔔 Notify requester (recipient type = USER)
        String title = "Request " + status.substring(0, 1).toUpperCase() + status.substring(1);
        String message = status.equalsIgnoreCase("approved") ?
                "Your request has been approved!" :
                "Your request has been rejected.";

        notificationService.createNotification(
                updatedRequest.getRequester().getId(),
                "USER",
                status.toUpperCase(),
                title,
                message
        );

        return mapToResponse(updatedRequest);
    }

    private RequestResponse mapToResponse(DonationRequest req) {
        return RequestResponse.builder()
                .id(req.getId())
                .donationId(req.getDonation().getId())
                .donationTitle(req.getDonation().getTitle())
                .requesterName(req.getRequester().getFullName())
                .status(req.getStatus())
                .message(req.getMessage())
                .createdAt(req.getCreatedAt())
                .build();
    }
}