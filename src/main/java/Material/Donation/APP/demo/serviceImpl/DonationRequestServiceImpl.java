package Material.Donation.APP.demo.serviceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Material.Donation.APP.demo.dto.request.CreateRequest;
import Material.Donation.APP.demo.dto.response.RequestResponse;
import Material.Donation.APP.demo.entity.*;
import Material.Donation.APP.demo.repository.*;
import Material.Donation.APP.demo.service.DonationRequestService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonationRequestServiceImpl implements DonationRequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final DonationRepository donationRepository;

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
                .build();

        return mapToResponse(requestRepository.save(request));
    }

    @Override
    public List<RequestResponse> getRequestsByRequester(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Custom query in RequestRepository needed: findByRequesterId
        return requestRepository.findByRequesterId(user.getId())
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<RequestResponse> getRequestsForMyDonations(String email) {
        // Find all requests where the owner of the donation is the person logged in
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

        // Match the donor of the item to the person currently logged in
        if (!req.getDonation().getDonor().getEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new RuntimeException("Unauthorized: You do not own this donation item.");
        }

        req.setStatus(status);

        if ("approved".equalsIgnoreCase(status)) {
            Donation donation = req.getDonation();
            donation.setStatus("pending"); // Item is now reserved
            donationRepository.save(donation);
        }

        return mapToResponse(requestRepository.save(req));
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