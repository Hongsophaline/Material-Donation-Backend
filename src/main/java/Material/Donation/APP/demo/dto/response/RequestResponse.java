package Material.Donation.APP.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestResponse {

    private UUID id;
    private UUID donationId;
    private String donationTitle;
    private String requesterName;
    private String status;
    private String message;
    private LocalDateTime createdAt;
}
