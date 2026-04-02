package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.dto.response.PickupResponse;
import Material.Donation.APP.demo.service.PickupService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pickups")
@RequiredArgsConstructor
public class PickupController {

    private final PickupService pickupService;

    @PostMapping("/schedule/{requestId}")
    public ResponseEntity<PickupResponse> schedule(
            @PathVariable UUID requestId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledAt,
            @RequestParam String address,
            Authentication auth) {
        return ResponseEntity.ok(pickupService.schedulePickup(requestId, auth.getName(), scheduledAt, address));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PickupResponse> updateStatus(
            @PathVariable UUID id,
            @RequestParam String status,
            Authentication auth) {
        return ResponseEntity.ok(pickupService.updateStatus(id, auth.getName(), status));
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<PickupResponse> getByRequest(@PathVariable UUID requestId) {
        return ResponseEntity.ok(pickupService.getByRequestId(requestId));
    }
    
}