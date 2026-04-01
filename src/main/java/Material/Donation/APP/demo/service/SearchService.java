package Material.Donation.APP.demo.service;

import Material.Donation.APP.demo.entity.Category;
import Material.Donation.APP.demo.entity.Donation;
import Material.Donation.APP.demo.entity.User;

import java.util.List;

public interface SearchService {

    List<Donation> searchDonations(User user, String keyword, Category category);
}