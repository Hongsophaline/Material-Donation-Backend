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

    // =========================
    // CREATE REQUEST
    // =========================
    @Override
    @Transactional
    public RequestResponse createRequest(String email, CreateRequest dto) {

        if (dto.getDonationId() == null) {
            throw new RuntimeException("DonationId is required");
        }

        User requester = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Donation donation = donationRepository.findById(dto.getDonationId())
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        // ❌ cannot request own item
        if (donation.getDonor().getEmail().equalsIgnoreCase(email)) {
            throw new RuntimeException("You cannot request your own donation");
        }

        DonationRequest request = DonationRequest.builder()
                .donation(donation)
                .requester(requester)
                .message(dto.getMessage())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        DonationRequest saved = requestRepository.save(request);

        // 🔔 NOTIFY DONOR (REAL-TIME)
        notificationService.createNotification(
                donation.getDonor().getId(),
                "DONOR",
                "REQUEST",
                "New Donation Request",
                requester.getFullName() + " requested: " + donation.getTitle()
        );

        return mapToResponse(saved);
    }

    // =========================
    // GET MY REQUESTS (SENDER)
    // =========================
    @Override
    public List<RequestResponse> getRequestsByRequester(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return requestRepository.findByRequesterId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // GET REQUESTS FOR MY DONATIONS (OWNER)
    // =========================
    @Override
    public List<RequestResponse> getRequestsForMyDonations(String email) {

        return requestRepository.findAll()
                .stream()
                .filter(req -> req.getDonation().getDonor().getEmail().equalsIgnoreCase(email))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // =========================
    // UPDATE REQUEST STATUS
    // =========================
    @Override
    @Transactional
    public RequestResponse updateRequestStatus(UUID requestId,
                                                String currentUserEmail,
                                                String status) {

        DonationRequest req = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // ❌ only owner can update
        if (!req.getDonation().getDonor().getEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new RuntimeException("Unauthorized access");
        }

        String normalizedStatus = status.toUpperCase();
        req.setStatus(normalizedStatus);

        Donation donation = req.getDonation();

        // ✅ if approved → reserve item
        if ("APPROVED".equals(normalizedStatus)) {
            donation.setStatus("RESERVED");
        }

        donationRepository.save(donation);
        DonationRequest updated = requestRepository.save(req);

        // 🔔 NOTIFY REQUESTER
        String title = "Request " + normalizedStatus;
        String message = "APPROVED".equals(normalizedStatus)
                ? "Your request has been approved 🎉"
                : "Your request has been rejected ❌";

        notificationService.createNotification(
                updated.getRequester().getId(),
                "USER",
                normalizedStatus,
                title,
                message
        );

        return mapToResponse(updated);
    }

    // =========================
    // MAP RESPONSE
    // =========================
    private RequestResponse mapToResponse(DonationRequest req) {
        return RequestResponse.builder()
                .id(req.getId())
                .donationTitle(req.getDonation().getTitle())
                .status(req.getStatus())
                .createdAt(req.getCreatedAt())
                .requesterId(req.getRequester().getId())
                .requesterName(req.getRequester().getFullName())
                .requesterPhone(req.getRequester().getPhone())
                .requesterAvatar(req.getRequester().getAvatarUrl())
                .donorId(req.getDonation().getDonor().getId())
                .donorName(req.getDonation().getDonor().getFullName())
                .donorPhone(req.getDonation().getDonor().getPhone())
                .donorAvatar(req.getDonation().getDonor().getAvatarUrl())
                .build();
    }
}