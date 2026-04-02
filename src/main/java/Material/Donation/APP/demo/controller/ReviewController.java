package Material.Donation.APP.demo.controller;

import Material.Donation.APP.demo.dto.request.ReviewRequest;
import Material.Donation.APP.demo.entity.Review;
import Material.Donation.APP.demo.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Create a review
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request) {
        Review review = reviewService.createReview(request);
        return ResponseEntity.ok(review);
    }

    // Get reviews for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    // Get reviews for a donation
    @GetMapping("/donation/{donationId}")
    public ResponseEntity<List<Review>> getReviewsByDonation(@PathVariable UUID donationId) {
        return ResponseEntity.ok(reviewService.getReviewsByDonation(donationId));
    }
}