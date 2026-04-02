package Material.Donation.APP.demo.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private UUID id;              // review id
    private UUID donationId;
    private UUID reviewerId;
    private UUID reviewedId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}