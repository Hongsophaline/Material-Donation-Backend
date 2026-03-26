package Material.Donation.APP.demo.serviceImpl;

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
    private final PasswordEncoder passwordEncoder; // 👈 Add this

    @Override
    public User registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }

        // Map manually or ensure your Mapper handles the password
        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .avatarUrl(request.getAvatarUrl())
                .password(passwordEncoder.encode(request.getPassword())) // 👈 Encode here
                .build();

        return userRepository.save(user);
    }
}