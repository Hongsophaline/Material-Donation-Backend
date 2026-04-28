package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.dto.request.CreateRequest;
import Material.Donation.APP.demo.dto.response.RequestResponse;
import Material.Donation.APP.demo.service.DonationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE})
public class RequestController {

    private final DonationRequestService requestService;

    @PostMapping
    public ResponseEntity<RequestResponse> create(
            Authentication authentication, 
            @RequestBody CreateRequest dto) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }

        String username = authentication.getName();
        return ResponseEntity.ok(requestService.createRequest(username, dto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<RequestResponse>> getMyRequests(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        return ResponseEntity.ok(requestService.getRequestsByRequester(username));
    }

    @GetMapping("/received")
    public ResponseEntity<List<RequestResponse>> getReceivedRequests(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        return ResponseEntity.ok(requestService.getRequestsForMyDonations(username));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<RequestResponse> approveRequest(
            @PathVariable UUID id, 
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        return ResponseEntity.ok(requestService.updateRequestStatus(id, username, "approved"));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<RequestResponse> rejectRequest(
            @PathVariable UUID id, 
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        return ResponseEntity.ok(requestService.updateRequestStatus(id, username, "rejected"));
    }
}