package Material.Donation.APP.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class NotificationResponse {  // <-- Response DTO
    private UUID id;
    private String type;
    private String title;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
}