package Material.Donation.APP.demo.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String fullName;
    private String password; // 👈 Must be here
    private String phone;
    private String avatarUrl;
}