package Material.Donation.APP.demo.service;

import Material.Donation.APP.demo.dto.request.RegisterRequest;
import Material.Donation.APP.demo.entity.User;

public interface UserService {
    User registerUser(RegisterRequest request);
}