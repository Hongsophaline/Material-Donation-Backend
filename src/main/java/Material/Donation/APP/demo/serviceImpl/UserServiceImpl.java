package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.dto.request.LoginRequest;
import Material.Donation.APP.demo.dto.request.RegisterRequest;
import Material.Donation.APP.demo.dto.request.UpdateProfileRequest;
import Material.Donation.APP.demo.dto.response.UserResponse;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.repository.UserRepository;
import Material.Donation.APP.demo.service.UserService;
import Material.Donation.APP.demo.config.JwtUtils;
import Material.Donation.APP.demo.dto.response.LoginResponse; // Ensure this import is correct
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public User registerUser(RegisterRequest request) {
        String identifier;

        if ("INDIVIDUAL".equalsIgnoreCase(request.getUserType())) {
            if (request.getDob() == null) throw new RuntimeException("Date of Birth is required for Individuals.");
            if (request.getPhone() == null) throw new RuntimeException("Phone Number is required.");
            identifier = request.getPhone(); 
        } else if ("ORGANIZATION".equalsIgnoreCase(request.getUserType())) {
            if (request.getEmail() == null) throw new RuntimeException("Organization Email is required.");
            identifier = request.getEmail();
        } else {
            throw new RuntimeException("Invalid User Type.");
        }

        if (userRepository.findByEmail(identifier).isPresent()) {
            throw new RuntimeException("This account is already registered!");
        }

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
public LoginResponse loginUser(LoginRequest request) {
    // 1. Find user
    User user = userRepository.findByEmail(request.getPhone())
            .orElseThrow(() -> new RuntimeException("User not found."));

    // 2. Verify password
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid phone number or password.");
    }

    // 3. Generate JWT Token
    String token = jwtUtils.generateToken(user.getEmail());

    // 4. Return the full response
    return LoginResponse.builder()
            .token(token)
            .message("Login successful")
            .userId(user.getId()) // Matches the DTO field name
            .fullName(user.getFullName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .avatarUrl(user.getAvatarUrl())
            .dob(user.getDob())
            .createdAt(user.getCreatedAt())
            .build();
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

    public UserResponse mapToResponse(User user, String token) {
        String displayEmail = (user.getEmail() != null && user.getEmail().contains("@")) 
                              ? user.getEmail() : null;

        return UserResponse.builder()
                .id(user.getId())
                .email(displayEmail) 
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .dob(user.getDob())
                .token(token)
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public void logout(String identifier) {
        System.out.println("User " + identifier + " logged out.");
    }
}