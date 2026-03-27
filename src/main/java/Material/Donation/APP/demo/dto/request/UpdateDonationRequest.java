package Material.Donation.APP.demo.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class UpdateDonationRequest {
    private String title;
    private String description;
    private UUID categoryId;
    private String condition;
    private String status; // e.g., "available", "pending", "collected"
    private String address;
    private Double latitude;
    private Double longitude;
}