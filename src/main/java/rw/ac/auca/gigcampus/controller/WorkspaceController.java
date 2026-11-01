package rw.ac.auca.gigcampus.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.ac.auca.gigcampus.domain.Workspace;
import rw.ac.auca.gigcampus.service.WorkspaceService;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService service;

    public WorkspaceController(WorkspaceService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestBody Workspace workspace) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(workspace));
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(
            @PathVariable UUID id) {

        Optional<Workspace> workspace = service.one(id);

        if (workspace.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("The workspace with that id does not exist");
        }

        return ResponseEntity.ok(workspace.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody Workspace workspace) {

        String result = service.update(id, workspace);

        if (result.equals(
                "The workspace with that id does not exist")) {

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
                "The workspace with that id does not exist")) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result);
        }

        return ResponseEntity.ok(result);
    }
}