package Material.Donation.APP.demo.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String email;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private String token; // 👈 The JWT goes here
    private LocalDateTime createdAt;
}