package Material.Donation.APP.demo.service;

import java.util.List;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.dto.response.DonationResponse;



public interface DonationService {
    DonationResponse createDonation(String email, DonationRequest request);
    List<DonationResponse> getAllDonations();
    List<DonationResponse> getDonationsByUser(String email);
}
