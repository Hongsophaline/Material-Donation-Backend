package Material.Donation.APP.demo.service;

import Material.Donation.APP.demo.dto.request.DonationRequest;
import Material.Donation.APP.demo.entity.Donation;

public interface DonationService {
    Donation createDonation(String userEmail, DonationRequest request);
}
