package Material.Donation.APP.demo.serviceImpl;

import Material.Donation.APP.demo.dto.request.LoginRequest;
import Material.Donation.APP.demo.dto.request.RegisterRequest;
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
        // 1. Validation: Prevent duplicate accounts
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        // 2. Build the User Entity
        // We use the PasswordEncoder here to hash the password before it hits the DB
        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .avatarUrl(request.getAvatarUrl())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        // 3. Persist and return
        // This returns the saved user containing the generated UUID and CreatedAt timestamp
        return userRepository.save(user);
    }

    @Override
public User loginUser(LoginRequest request) {
    // Find user - This will now work because findByEmail returns Optional<User>
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

    // Validate password
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid password");
    }

    return user;
}

    @Override
    public Object getUserProfile(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfile'");
    }


}