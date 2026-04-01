package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.dto.request.LoginRequest;
import Material.Donation.APP.demo.dto.request.RegisterRequest;
import Material.Donation.APP.demo.dto.request.UpdateProfileRequest;
import Material.Donation.APP.demo.dto.response.UserResponse;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(RegisterRequest request) {
        String identifier;

        // 1. Differentiate between Individual and Organization
        if ("INDIVIDUAL".equalsIgnoreCase(request.getUserType())) {
            if (request.getDob() == null) throw new RuntimeException("Date of Birth is required for Individuals.");
            if (request.getPhone() == null) throw new RuntimeException("Phone Number is required.");
            identifier = request.getPhone(); 
        } else if ("ORGANIZATION".equalsIgnoreCase(request.getUserType())) {
            if (request.getEmail() == null) throw new RuntimeException("Organization Email is required.");
            identifier = request.getEmail();
            request.setDob(null); // Orgs don't have a birthday
        } else {
            throw new RuntimeException("Invalid User Type.");
        }

        // 2. Duplicate check
        if (userRepository.findByEmail(identifier).isPresent()) {
            throw new RuntimeException("This account is already registered!");
        }

        // 3. Save Entity
        User user = User.builder()
                .email(identifier) 
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .userType(request.getUserType().toUpperCase())
                .dob(request.getDob())
                .avatarUrl(request.getAvatarUrl())
                .password(passwordEncoder.encode(request.getPassword()))
                .status("approved")
                .build();

        return userRepository.save(user);
    }

    @Override
    public User loginUser(LoginRequest request) {
        // Use phone as the identifier for the login search
        return userRepository.findByEmail(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Override
    public UserResponse getUserProfile(String identifier) {
        User user = userRepository.findByEmail(identifier)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user, null);
    }

    @Override
    @Transactional
    public UserResponse updateProfile(String identifier, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(identifier)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());

        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser, null);
    }

    @Override
    @Transactional
    public User updateUserProfile(String identifier, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(identifier)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());

        return userRepository.save(user);
    }

    /**
     * Unified Helper to convert Entity to Response DTO
     * Handles the 'dob' mapping and 'email vs phone' display logic
     */
    public UserResponse mapToResponse(User user, String token) {
        // Hide the phone number if it's stored in the email column (for Individuals)
        String displayEmail = (user.getEmail() != null && user.getEmail().contains("@")) 
                              ? user.getEmail() : null;

        return UserResponse.builder()
                .id(user.getId())
                .email(displayEmail) 
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .dob(user.getDob()) // Added dob to response
                .token(token)
                .createdAt(user.getCreatedAt())
                .build();
    }

    //logout
    @Override
    public void logout (String identifier) {
        // Since JWT is stateless, we don't need to do anything server-side for logout.
        // The client should simply delete the token on their end.
        System.out.println("User " + identifier + " logged out.");
    }
}