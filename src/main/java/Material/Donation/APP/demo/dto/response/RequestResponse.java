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
    private String requesterName;
    
    // ADD THESE THREE FIELDS BELOW:
    private String requesterPhone; 
    private String donorName;      
    private String donorPhone;     
    
    private String status;
    private LocalDateTime createdAt;
}