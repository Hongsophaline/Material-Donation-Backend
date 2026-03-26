package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.config.JwtUtils;
import Material.Donation.APP.demo.dto.request.LoginRequest;
import Material.Donation.APP.demo.dto.request.RegisterRequest;
import Material.Donation.APP.demo.dto.response.UserResponse;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils; // 👈 Fixed: Use the actual class, not Object

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        // 1. Register the user via service
        User user = userService.registerUser(request);
        
        // 2. Generate the token using the injected utility
        String token = jwtUtils.generateToken(user.getEmail());

        // 3. Map to UserResponse DTO
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .token(token) 
                .createdAt(user.getCreatedAt())
                .build();

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
    // 1. Authenticate user
    User user = userService.loginUser(request);

    // 2. Generate token
    String token = jwtUtils.generateToken(user.getEmail());

    // 3. Return response (hiding the password)
    UserResponse response = UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .fullName(user.getFullName())
            .phone(user.getPhone())
            .avatarUrl(user.getAvatarUrl())
            .token(token)
            .createdAt(user.getCreatedAt())
            .build();

    return ResponseEntity.ok(response);
}
}