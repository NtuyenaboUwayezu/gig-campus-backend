package rw.ac.auca.gigcampus.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.gigcampus.domain.Rating;
import rw.ac.auca.gigcampus.service.RatingService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestParam UUID gigId,
            @RequestBody Rating rating) {

        String result = service.save(rating, gigId);

        if (result.equals("The gig with that id does not exist")) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result);
        }

        if (result.equals("Ratings are only for DELIVERED gigs")) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @GetMapping("/employer/{phone}")
public ResponseEntity<?> byEmployer(
        @PathVariable String phone) {

    return ResponseEntity.ok(
            service.byEmployer(phone));
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> delete(
        @PathVariable UUID id) {

    String result = service.delete(id);

    if (result.equals("The rating with that id does not exist")) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(result);
    }

    return ResponseEntity.ok(result);
}

}