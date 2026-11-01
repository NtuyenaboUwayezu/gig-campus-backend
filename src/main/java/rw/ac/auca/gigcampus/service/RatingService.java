package rw.ac.auca.gigcampus.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.gigcampus.domain.Gig;
import rw.ac.auca.gigcampus.domain.GigStatus;
import rw.ac.auca.gigcampus.domain.Rating;
import rw.ac.auca.gigcampus.repository.GigRepository;
import rw.ac.auca.gigcampus.repository.RatingRepository;

@Service
public class RatingService {

    private final RatingRepository ratings;
    private final GigRepository gigs;

    public RatingService(RatingRepository ratings, GigRepository gigs) {

        this.ratings = ratings;
        this.gigs = gigs;
    }

    @Transactional
    public String save(Rating rating, UUID gigId) {

        Optional<Gig> found = gigs.findById(gigId);

        if (found.isEmpty()) {
            return "The gig with that id does not exist";
        }

        Gig gig = found.get();

        if (gig.getStatus() != GigStatus.DELIVERED) {
            return "Ratings are only for DELIVERED gigs";
        }

        rating.setRatingDate(LocalDate.now());
        rating.setGig(gig);

        ratings.save(rating);

        return "Rating saved successfully";
    }

    public List<Rating> byEmployer(String phone) {
    return ratings.findByEmployerPhone(phone);
}

@Transactional
public String delete(UUID id) {

    Optional<Rating> found = ratings.findById(id);

    if (found.isEmpty()) {
        return "The rating with that id does not exist";
    }

    ratings.delete(found.get());

    return "Rating deleted successfully";
}

}