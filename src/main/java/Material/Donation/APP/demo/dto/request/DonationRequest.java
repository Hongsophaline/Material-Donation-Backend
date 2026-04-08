package Material.Donation.APP.demo.dto.request;

import lombok.Data;
import java.util.UUID;

@Data // 👈 This generates getTitle(), getCategoryId(), etc.
public class DonationRequest {
    private String title;
    private String description;
    private UUID categoryId;
    private String condition;
    private String address;
    private Double latitude;
    private Double longitude;
}