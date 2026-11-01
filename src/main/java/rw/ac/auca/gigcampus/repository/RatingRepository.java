package rw.ac.auca.gigcampus.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.ac.auca.gigcampus.domain.Rating;
import rw.ac.auca.gigcampus.domain.Gig;

public interface RatingRepository
        extends JpaRepository<Rating, UUID> {

    @Query("""
        SELECT r
        FROM Rating r
        WHERE r.gig.employer.phone = :phone
        ORDER BY r.ratingDate DESC
        """)
    List<Rating> findByEmployerPhone(
            @Param("phone") String phone);

    boolean existsByGig(Gig gig);
    
}