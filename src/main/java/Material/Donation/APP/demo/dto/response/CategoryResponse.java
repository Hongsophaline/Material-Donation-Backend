package Material.Donation.APP.demo.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class CategoryResponse {
    private UUID id;
    private String name;
}