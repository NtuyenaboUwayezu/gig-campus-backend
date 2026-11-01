package rw.ac.auca.gigcampus.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import rw.ac.auca.gigcampus.domain.GigStatus;
import rw.ac.auca.gigcampus.repository.EmployerRepository;
import rw.ac.auca.gigcampus.repository.GigRepository;
import rw.ac.auca.gigcampus.repository.RatingRepository;
import rw.ac.auca.gigcampus.repository.StudentRepository;
import rw.ac.auca.gigcampus.repository.WorkspaceRepository;

@Service
public class DashboardService {

    private final EmployerRepository employers;
    private final StudentRepository students;
    private final WorkspaceRepository workspaces;
    private final GigRepository gigs;
    private final RatingRepository ratings;

    public DashboardService(
            EmployerRepository employers,
            StudentRepository students,
            WorkspaceRepository workspaces,
            GigRepository gigs,
            RatingRepository ratings) {

        this.employers = employers;
        this.students = students;
        this.workspaces = workspaces;
        this.gigs = gigs;
        this.ratings = ratings;
    }

    public Map<String, Long> summary() {

        Map<String, Long> result =
                new LinkedHashMap<>();

        result.put(
                "employers",
                employers.count());

        result.put(
                "students",
                students.count());

        result.put(
                "freeWorkspaces",
                (long) workspaces
                        .findFreeWorkspaces()
                        .size());

        result.put(
                "gigsToday",
                gigs.countByGigDate(
                        LocalDate.now()));

        result.put(
                "open",
                gigs.countByStatus(
                        GigStatus.OPEN));

        result.put(
                "ratings",
                ratings.count());

        return result;
    }
}