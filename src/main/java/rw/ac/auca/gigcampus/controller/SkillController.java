package rw.ac.auca.gigcampus.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.gigcampus.domain.Skill;
import rw.ac.auca.gigcampus.service.SkillService;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestBody Skill skill) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(skill));
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(
            @PathVariable UUID id) {

        Optional<Skill> skill = service.one(id);

        if (skill.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("The skill with that id does not exist");
        }

        return ResponseEntity.ok(skill.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody Skill skill) {

        String result = service.update(id, skill);

        if (result.equals(
                "The skill with that id does not exist")) {

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

        if (result.equals(
                "The skill with that id does not exist")) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/unused")
public ResponseEntity<?> unusedSkills() {

    return ResponseEntity.ok(
            service.unusedSkills());
}

}