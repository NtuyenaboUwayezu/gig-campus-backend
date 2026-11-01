package rw.ac.auca.gigcampus.controller;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.gigcampus.domain.Employer;
import rw.ac.auca.gigcampus.service.EmployerService;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    private final EmployerService service;

    public EmployerController(EmployerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestBody Employer employer) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(employer));
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(
            @PathVariable UUID id) {

        Optional<Employer> employer = service.one(id);

        if (employer.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("The employer with that id does not exist");
        }

        return ResponseEntity.ok(employer.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody Employer employer) {

        String result = service.update(id, employer);

        if (result.equals("The employer with that id does not exist")) {

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

        if (result.equals("The employer with that id does not exist")) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-organization")
public ResponseEntity<?> byOrganization(
        @RequestParam String organization) {

    return ResponseEntity.ok(
            service.byOrganization(organization));
}

@GetMapping("/of-student/{studentId}")
public ResponseEntity<?> ofStudent(
        @PathVariable UUID studentId) {

    List<Employer> result =
            service.ofStudent(studentId);

    if (result == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("The student with that id does not exist");
    }

    return ResponseEntity.ok(result);
}

}