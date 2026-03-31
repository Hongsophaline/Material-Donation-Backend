package Material.Donation.APP.demo.service;

import Material.Donation.APP.demo.dto.request.CreateRequest;
import Material.Donation.APP.demo.dto.response.RequestResponse;
import java.util.List;
import java.util.UUID;

public interface DonationRequestService {
    RequestResponse createRequest(String email, CreateRequest dto);
    
    List<RequestResponse> getRequestsByRequester(String email);
    
    // ADD THIS LINE
    List<RequestResponse> getRequestsForMyDonations(String email);
    
    RequestResponse updateRequestStatus(UUID requestId, String donorEmail, String status);
}