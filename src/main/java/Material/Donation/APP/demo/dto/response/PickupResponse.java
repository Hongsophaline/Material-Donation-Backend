package Material.Donation.APP.demo.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupResponse {
    private UUID id;
    private UUID requestId;
    private String donationTitle;
    private String requesterName;
    private String donorName;
    private LocalDateTime scheduledAt;
    private String pickupAddress;
    private String status;
    private LocalDateTime completedAt;
}