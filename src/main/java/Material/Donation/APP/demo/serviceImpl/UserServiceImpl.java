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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest request) {
        String identifier;

        // 1. Logic for INDIVIDUAL (No Email)
        if ("INDIVIDUAL".equalsIgnoreCase(request.getUserType())) {
            if (request.getDob() == null) {
                throw new RuntimeException("Date of Birth is required for Individuals.");
            }
            if (request.getPhone() == null || request.getPhone().isBlank()) {
                throw new RuntimeException("Phone Number is required for Individuals.");
            }
            // Use phone as the unique identifier since email is missing
            identifier = request.getPhone(); 
        } 
        
        // 2. Logic for ORGANIZATION (Has Email)
        else if ("ORGANIZATION".equalsIgnoreCase(request.getUserType())) {
            if (request.getEmail() == null || request.getEmail().isBlank()) {
                throw new RuntimeException("Organization Email is required.");
            }
            identifier = request.getEmail();
            request.setDob(null); // Organizations don't have a DOB
        } else {
            throw new RuntimeException("Invalid User Type.");
        }

        // 3. Check if this user already exists
        if (userRepository.findByEmail(identifier).isPresent()) {
            throw new RuntimeException("This account (Email/Phone) is already registered!");
        }

        // 4. Build and Save
        User user = User.builder()
                .email(identifier) // Storing either Email or Phone here
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .userType(request.getUserType().toUpperCase())
                .dob(request.getDob())
                .password(passwordEncoder.encode(request.getPassword()))
                .status("approved")
                .build();

        return userRepository.save(user);
    }

    @Override
    public User loginUser(LoginRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loginUser'");
    }

    @Override
    public Object getUserProfile(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfile'");
    }

    @Override
    public User updateUserProfile(String email, UpdateProfileRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfile'");
    }

    @Override
    public UserResponse updateProfile(String email, UpdateProfileRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProfile'");
    }
}