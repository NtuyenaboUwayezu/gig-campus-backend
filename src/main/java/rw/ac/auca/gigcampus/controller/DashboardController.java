package rw.ac.auca.gigcampus.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.ac.auca.gigcampus.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {

        this.service = service;
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>>
            summary() {

        return ResponseEntity.ok(service.summary());
    }
}