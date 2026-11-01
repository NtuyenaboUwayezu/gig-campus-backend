package rw.ac.auca.gigcampus.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.gigcampus.domain.Student;
import rw.ac.auca.gigcampus.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) {
        this.service = service;
    }
    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestBody Student student) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(student));
    }
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(service.all());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> one(
            @PathVariable UUID id) {
        Optional<Student> student = service.one(id);
        if (student.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("The student with that id does not exist");
        }
        return ResponseEntity.ok(student.get());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody Student student) {
        String result = service.update(id, student);
        if (result.equals("The student with that id does not exist")) {
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

        if (result.equals("The student with that id does not exist")) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-skill")
public ResponseEntity<?> bySkill(
        @RequestParam String name) {

    return ResponseEntity.ok(
            service.bySkill(name));
}

@PutMapping("/{studentId}/skills/{skillId}")
public ResponseEntity<String> addSkill(
        @PathVariable UUID studentId,
        @PathVariable UUID skillId) {

    String result = service.addSkill(studentId, skillId);

    if (result.equals("The student with that id does not exist")
            || result.equals("That skill does not exist")) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(result);
    }

    if (result.equals("The student already has that skill")) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(result);
    }

    return ResponseEntity.ok(result);
}

@PatchMapping("/{studentId}/workspace")
public ResponseEntity<String> changeWorkspace(
        @PathVariable UUID studentId,
        @RequestParam int workspaceNumber) {

    String result = service.changeWorkspace(studentId, workspaceNumber);

    if (result.equals("The student with that id does not exist")
            || result.equals("The workspace with that workspaceNumber does not exist")) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(result);
    }

    if (result.equals("That workspace is taken by another student")) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(result);
    }

    return ResponseEntity.ok(result);
}

}