package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.config.JwtUtils;
import Material.Donation.APP.demo.dto.request.LoginRequest;
import Material.Donation.APP.demo.dto.request.RegisterRequest;
import Material.Donation.APP.demo.dto.request.UpdateProfileRequest;
import Material.Donation.APP.demo.dto.response.LoginResponse;
import Material.Donation.APP.demo.dto.response.UserResponse;
import Material.Donation.APP.demo.entity.User;
import Material.Donation.APP.demo.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final JwtUtils jwtUtils; 

 @PostMapping("/register")
public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) { 
    User user = userService.registerUser(request);
    String token = jwtUtils.generateToken(user.getEmail());
    
    // Call the updated mapToResponse helper
    return ResponseEntity.ok(userService.mapToResponse(user, token));
}

@PostMapping("/login")
public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    // This calls the service we just fixed and returns the JSON with the token
    return ResponseEntity.ok(userService.loginUser(request));
}
    // --- PROFILE ENDPOINTS ---

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getMyProfile(Principal principal) {
        // principal.getName() returns the 'identifier' (Email or Phone) from the JWT
        return ResponseEntity.ok(userService.getUserProfile(principal.getName()));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateMyProfile(Principal principal, @RequestBody UpdateProfileRequest request) {
        // Updates the user identified by the token
        return ResponseEntity.ok(userService.updateProfile(principal.getName(), request));
    }
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(Principal principal) {
        // Logically, we just acknowledge the logout. 
        // The client must delete the JWT from their storage (LocalStorage/Cookies).
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully. User: " + principal.getName());
        return ResponseEntity.ok(response);
    }
}