package Material.Donation.APP.demo.service;

import Material.Donation.APP.demo.dto.request.LoginRequest;
import Material.Donation.APP.demo.dto.request.RegisterRequest;
import Material.Donation.APP.demo.dto.request.UpdateProfileRequest;
import Material.Donation.APP.demo.dto.response.UserResponse;
import Material.Donation.APP.demo.entity.User;

public interface UserService {
    User registerUser(RegisterRequest request);
    User loginUser(LoginRequest request);
    Object getUserProfile(String email);
    User updateUserProfile(String email, UpdateProfileRequest request);
    UserResponse updateProfile(String email, UpdateProfileRequest request);
    void logout(String identifier);
    
}