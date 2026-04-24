package Material.Donation.APP.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestResponse {
    private UUID id;
    private String donationTitle;
    private String status;
    private LocalDateTime createdAt;
    
    // ADD THESE FIELDS
    private UUID requesterId;
    private String requesterName;
    private String requesterPhone;
    private String requesterAvatar;

    private UUID donorId;
    private String donorName;
    private String donorPhone;
    private String donorAvatar;
}