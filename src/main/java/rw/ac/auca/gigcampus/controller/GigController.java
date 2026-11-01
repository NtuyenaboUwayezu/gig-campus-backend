package rw.ac.auca.gigcampus.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.gigcampus.domain.Gig;
import rw.ac.auca.gigcampus.domain.GigStatus;
import rw.ac.auca.gigcampus.service.GigService;

@RestController
@RequestMapping("/api/gigs")
public class GigController {

    private final GigService service;

    public GigController(GigService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestBody Gig gig) {

        String result = service.save(gig);

        if (result.equals("Gig date cannot be in the past")) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }

        if (result.equals("Student already has a gig on that date")) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(result);
        }

        if (result.equals("The student with that id does not exist")
                || result.equals("The employer with that id does not exist")) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Gig>> all() {

        return ResponseEntity.ok(
                service.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(
            @PathVariable UUID id) {

        Optional<Gig> found =
                service.one(id);

        if (found.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                    "The gig with that id does not exist");
        }

        return ResponseEntity.ok(
                found.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody Gig gig) {

        String result =
                service.update(id, gig);

        if (result.equals("The gig with that id does not exist")
                || result.equals("The student with that id does not exist")
                || result.equals("The employer with that id does not exist")) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @PathVariable UUID id) {

        String result = service.delete(id);

        if (result.equals("The gig with that id does not exist")) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result);
        }

        if (result.equals("That gig has ratings, delete them first")) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Gig>> byStatus(
            @RequestParam GigStatus status) {

        return ResponseEntity.ok(service.byStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<Gig>> between(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);

        LocalDate endDate = LocalDate.parse(end);

        return ResponseEntity.ok(service.between(startDate, endDate));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(
            @PathVariable UUID id,
            @RequestParam GigStatus status) {

        String result =
                service.changeStatus(id, status);

        if (result.equals("The gig with that id does not exist")) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result);
        }

        if (result.startsWith("Cannot change status from")) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>>
            statsByStatus() {

        return ResponseEntity.ok(service.statsByStatus());
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(
            @RequestParam UUID studentId,
            @RequestParam String date) {

        LocalDate gigDate = LocalDate.parse(date);

        return ResponseEntity.ok(
                service.cancelDay(studentId, gigDate));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Gig>> page(
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")
            int size,
            @RequestParam(defaultValue = "gigDate,desc")
            String sort) {

        String[] parts = sort.split(",");

        String field = parts[0];

        Sort.Direction direction = Sort.Direction.DESC;

        if (parts.length > 1
                && parts[1].equalsIgnoreCase("asc")) {

            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));

        return ResponseEntity.ok(service.page(pageable));
    }
}