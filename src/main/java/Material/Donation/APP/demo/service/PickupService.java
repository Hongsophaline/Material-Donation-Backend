package Material.Donation.APP.demo.service;

import Material.Donation.APP.demo.dto.response.PickupResponse;
import java.time.LocalDateTime;
import java.util.UUID;

public interface PickupService {
    PickupResponse schedulePickup(UUID requestId, String donorEmail, LocalDateTime scheduledAt, String address);
    PickupResponse updateStatus(UUID pickupId, String email, String status);
    PickupResponse getByRequestId(UUID requestId);
    Object schedule(UUID requestId, LocalDateTime scheduledAt, String address);
}