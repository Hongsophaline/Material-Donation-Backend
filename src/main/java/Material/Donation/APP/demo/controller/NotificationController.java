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

    // =========================
    // 📩 GET USER NOTIFICATIONS
    // =========================
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getMyNotifications(userId));
    }

    // =========================
    // 🔴 MARK ONE AS READ
    // =========================
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // 🔴 MARK ALL AS READ
    // =========================
    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable UUID userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // 🔔 SEND NOTIFICATION (TEST / MANUAL)
    // =========================
    @PostMapping("/send/{userId}")
    public ResponseEntity<Void> sendNotification(
            @PathVariable UUID userId,
            @RequestParam String role,
            @RequestParam String type,
            @RequestParam String title,
            @RequestParam String message) {

        notificationService.createNotification(userId, role, type, title, message);
        return ResponseEntity.ok().build();
    }

    // =========================
    // 🔴 UNREAD COUNT (BADGE SUPPORT)
    // =========================
    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.countUnreadNotifications(userId));
    }
}