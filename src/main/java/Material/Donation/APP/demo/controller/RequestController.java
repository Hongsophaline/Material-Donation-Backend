

package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.dto.request.CreateRequest;
import Material.Donation.APP.demo.dto.response.RequestResponse;
import Material.Donation.APP.demo.service.DonationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/requests")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class RequestController {

    private final DonationRequestService requestService;

    @PostMapping
    public ResponseEntity<RequestResponse> create(@RequestBody CreateRequest dto, Principal principal) {
        return ResponseEntity.ok(requestService.createRequest(principal.getName(), dto));
    }

    // Requests SENT BY the current user
    @GetMapping("/my")
    public ResponseEntity<List<RequestResponse>> getMyRequests(Principal principal) {
        return ResponseEntity.ok(requestService.getRequestsByRequester(principal.getName()));
    }

    // Requests RECEIVED FOR the current user's donations
    @GetMapping("/received")
    public ResponseEntity<List<RequestResponse>> getReceivedRequests(Principal principal) {
        return ResponseEntity.ok(requestService.getRequestsForMyDonations(principal.getName()));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<RequestResponse> approveRequest(
            @PathVariable UUID id, 
            Principal principal) {
        return ResponseEntity.ok(requestService.updateRequestStatus(id, principal.getName(), "approved"));
    }

    // NEW: Reject Request
    @PatchMapping("/{id}/reject")
    public ResponseEntity<RequestResponse> rejectRequest(
            @PathVariable UUID id, 
            Principal principal) {
        return ResponseEntity.ok(requestService.updateRequestStatus(id, principal.getName(), "rejected"));
    }
}