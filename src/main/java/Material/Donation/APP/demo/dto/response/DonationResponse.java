package Material.Donation.APP.demo.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DonationResponse {
    private UUID id;
    private String title;
    private String description;
    private UUID categoryId;
    private String condition;
    private String status;
    private String address;
    private String donorName;
    private List<String> imageUrls; // <--- Add this
    private LocalDateTime createdAt;
}