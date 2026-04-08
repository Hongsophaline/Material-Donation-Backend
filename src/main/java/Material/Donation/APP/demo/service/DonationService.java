package Material.Donation.APP.demo.service;

import java.util.List;
import java.util.UUID;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.dto.request.UpdateDonationRequest;
import Material.Donation.APP.demo.dto.response.DonationResponse;

public interface DonationService {
    DonationResponse createDonation(String email, DonationRequest request);
    List<DonationResponse> getAllDonations(UUID categoryId); // Updated signature
    List<DonationResponse> getDonationsByUser(String email);
    DonationResponse updateDonation(UUID id, String email, UpdateDonationRequest request);
    void deleteDonation(UUID id, String email);
    Object searchDonations(String keyword, UUID categoryId);
    void addDonationImage(UUID id, String name, String imageUrl);
}
