package Material.Donation.APP.demo.dto.request;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {
    private UUID donationId;
    private UUID reviewerId;  // usually current logged-in user
    private UUID reviewedId;  // the donation owner
    private int rating;
    private String comment;
}