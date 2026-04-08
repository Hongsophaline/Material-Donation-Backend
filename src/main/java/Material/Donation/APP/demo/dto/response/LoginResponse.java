package Material.Donation.APP.demo.dto.response;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String message;

    // Add these fields so the builder works
    private UUID userId; 
    private String fullName;
    private String email;
    private String phone;
    private String avatarUrl;
    private LocalDate dob;
    private LocalDateTime createdAt;
}