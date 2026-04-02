package Material.Donation.APP.demo.dto.request;

import lombok.Data;

@Data // This automatically creates getPassword() for you
public class LoginRequest {
    private String phone;
    private String password;
}