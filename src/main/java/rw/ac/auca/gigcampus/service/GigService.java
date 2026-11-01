package rw.ac.auca.gigcampus.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.gigcampus.domain.Employer;
import rw.ac.auca.gigcampus.domain.Gig;
import rw.ac.auca.gigcampus.domain.GigStatus;
import rw.ac.auca.gigcampus.domain.Student;
import rw.ac.auca.gigcampus.repository.EmployerRepository;
import rw.ac.auca.gigcampus.repository.GigRepository;
import rw.ac.auca.gigcampus.repository.RatingRepository;
import rw.ac.auca.gigcampus.repository.StudentRepository;

@Service
public class GigService {

    private final GigRepository gigs;
    private final EmployerRepository employers;
    private final StudentRepository students;
    private final RatingRepository ratings;

    public GigService(GigRepository gigs, EmployerRepository employers, StudentRepository students,
            RatingRepository ratings) {

        this.gigs = gigs;
        this.employers = employers;
        this.students = students;
        this.ratings = ratings;
    }

    public List<Gig> all() {
        return gigs.findAll();
    }

    public Optional<Gig> one(UUID id) {
        return gigs.findById(id);
    }

    @Transactional
    public String save(Gig input) {

        if (input.getGigDate() != null
                && input.getGigDate().isBefore(LocalDate.now())) {

            return "Gig date cannot be in the past";
        }

        Student student =
                resolveStudent(input.getStudent());

        if (student == null) {
            return "The student with that id does not exist";
        }

        Employer employer =
                resolveEmployer(input.getEmployer());

        if (employer == null) {
            return "The employer with that id does not exist";
        }

        boolean alreadyBooked =
                gigs.existsByStudent_IdAndGigDateAndStatusNot(
                        student.getId(),
                        input.getGigDate(),
                        GigStatus.WITHDRAWN);

        if (alreadyBooked) {
            return "Student already has a gig on that date";
        }

        Gig gig = new Gig();

        gig.setGigDate(input.getGigDate());
        gig.setStatus(GigStatus.OPEN);
        gig.setTitle(input.getTitle());
        gig.setBudget(input.getBudget());
        gig.setEmployer(employer);
        gig.setStudent(student);

        gigs.save(gig);

        return "Gig saved successfully";
    }

    @Transactional
    public String update(UUID id, Gig input) {

        Optional<Gig> found = gigs.findById(id);

        if (found.isEmpty()) {
            return "The gig with that id does not exist";
        }

        Student student =
                resolveStudent(input.getStudent());

        if (student == null) {
            return "The student with that id does not exist";
        }

        Employer employer =
                resolveEmployer(input.getEmployer());

        if (employer == null) {
            return "The employer with that id does not exist";
        }

        Gig gig = found.get();

        gig.setGigDate(input.getGigDate());
        gig.setTitle(input.getTitle());
        gig.setBudget(input.getBudget());
        gig.setEmployer(employer);
        gig.setStudent(student);

        gigs.save(gig);

        return "Gig updated successfully";
    }

    @Transactional
    public String delete(UUID id) {

        Optional<Gig> found = gigs.findById(id);

        if (found.isEmpty()) {
            return "The gig with that id does not exist";
        }

        Gig gig = found.get();

        if (ratings.existsByGig(gig)) {
            return "That gig has ratings, delete them first";
        }

        gigs.delete(gig);

        return "Gig deleted successfully";
    }

    public List<Gig> byStatus(GigStatus status) {

        return gigs.findByStatusOrderByGigDateAsc(status);
    }

    public List<Gig> between(LocalDate start, LocalDate end) {

        return gigs.findByGigDateBetweenOrderByGigDateAsc(start, end);
    }

    @Transactional
    public String changeStatus(UUID id, GigStatus newStatus) {

        Optional<Gig> found = gigs.findById(id);

        if (found.isEmpty()) {
            return "The gig with that id does not exist";
        }

        Gig gig = found.get();
        GigStatus currentStatus = gig.getStatus();

        boolean allowed = false;

        if (currentStatus == GigStatus.OPEN) {
            allowed = newStatus == GigStatus.ASSIGNED
                    || newStatus == GigStatus.WITHDRAWN;
        }

        if (currentStatus == GigStatus.ASSIGNED) {
            allowed = newStatus == GigStatus.DELIVERED
                    || newStatus == GigStatus.WITHDRAWN;
        }

        if (!allowed) {
            return "Cannot change status from "
                    + currentStatus
                    + " to "
                    + newStatus;
        }

        gig.setStatus(newStatus);
        gigs.save(gig);

        return "Gig status updated successfully";
    }

    public List<Object[]> statsByStatus() {

        return gigs.countGigsByStatus();
    }

    @Transactional
    public String cancelDay(UUID studentId, LocalDate date) {

        int numberCancelled =
                gigs.cancelStudentDay(studentId, date);

        return numberCancelled + " gigs cancelled";
    }

    public Page<Gig> page(Pageable pageable) {

        return gigs.findAll(pageable);
    }

    private Student resolveStudent(Student input) {

        if (input == null
                || input.getId() == null) {

            return null;
        }

        return students
                .findById(input.getId())
                .orElse(null);
    }

    private Employer resolveEmployer(Employer input) {

        if (input == null
                || input.getId() == null) {

            return null;
        }

        return employers
                .findById(input.getId())
                .orElse(null);
    }
}