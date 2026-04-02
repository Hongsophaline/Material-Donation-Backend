package Material.Donation.APP.demo.dto.request;

import lombok.Data;

@Data
public class NotificationRequest {  // <-- Request DTO
    private String type;
    private String title;
    private String message;
}