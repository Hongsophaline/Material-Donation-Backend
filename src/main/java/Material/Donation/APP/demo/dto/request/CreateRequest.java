package Material.Donation.APP.demo.dto.request;

import lombok.Data;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateRequest {
    @NotNull(message = "Donation ID is required")
    private UUID donationId;
    private String message;
}