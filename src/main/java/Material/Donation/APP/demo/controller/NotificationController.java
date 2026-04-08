package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.entity.Notification;
import Material.Donation.APP.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getMyNotifications(userId));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
@PatchMapping("/user/{userId}/read-all")
public ResponseEntity<Void> markAllAsRead(@PathVariable UUID userId) {
    notificationService.markAllAsRead(userId);
    return ResponseEntity.noContent().build();
}
    @PostMapping("/send/{userId}")
    public ResponseEntity<Void> sendNotification(
            @PathVariable UUID userId,
            @RequestParam String role,
            @RequestParam String type,
            @RequestParam String title,
            @RequestParam String message) {
        
        // Use the unified method name
        notificationService.createNotification(userId, role, type, title, message);
        return ResponseEntity.ok().build();
    }
}