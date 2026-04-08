package Material.Donation.APP.demo.service;

import Material.Donation.APP.demo.dto.request.ReviewRequest;
import Material.Donation.APP.demo.entity.Review;
import Material.Donation.APP.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(ReviewRequest request) {
        Review review = Review.builder()
                .donationId(request.getDonationId())
                .reviewerId(request.getReviewerId())
                .reviewedId(request.getReviewedId())
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByUser(UUID reviewedId) {
        return reviewRepository.findByReviewedId(reviewedId);
    }

    public List<Review> getReviewsByDonation(UUID donationId) {
        return reviewRepository.findByDonationId(donationId);
    }
}