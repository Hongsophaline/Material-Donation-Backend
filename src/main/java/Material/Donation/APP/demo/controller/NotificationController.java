package Material.Donation.APP.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import Material.Donation.APP.demo.entity.Notification;
import Material.Donation.APP.demo.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Get notifications for a user or donor
    @GetMapping("/{role}/{userId}")
    public List<Notification> getNotifications(@PathVariable String role, @PathVariable UUID userId) {
        return notificationService.getNotifications(userId, role);
    }

    // Create notification for a user or donor
    @PostMapping("/{role}/{userId}")
    public void createNotification(@PathVariable String role,
                                   @PathVariable UUID userId,
                                   @RequestBody Notification dto) {
        notificationService.createNotification(userId, role, dto.getType(), dto.getTitle(), dto.getMessage());
    }

    // Mark notification as read
    @PatchMapping("/{notificationId}/read")
    public void markAsRead(@PathVariable UUID notificationId) {
        notificationService.markAsRead(notificationId);
    }
}