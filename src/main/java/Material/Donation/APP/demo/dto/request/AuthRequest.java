package Material.Donation.APP.demo.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String avatar;
    // Matches "fullName" in your JSON
}