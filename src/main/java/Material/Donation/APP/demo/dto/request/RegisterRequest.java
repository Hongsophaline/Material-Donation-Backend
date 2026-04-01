package Material.Donation.APP.demo.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String fullName;
    private String phone;
    private String password;
    private String userType; // "INDIVIDUAL" or "ORGANIZATION"
    
    // Optional depending on type
    private String email; 
    private LocalDate dob;
    public String getAvatarUrl() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAvatarUrl'");
    }
}