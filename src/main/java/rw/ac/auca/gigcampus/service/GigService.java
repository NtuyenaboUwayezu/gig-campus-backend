package rw.ac.auca.gigcampus.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.gigcampus.domain.Employer;
import rw.ac.auca.gigcampus.domain.Gig;
import rw.ac.auca.gigcampus.domain.Student;
import rw.ac.auca.gigcampus.repository.EmployerRepository;
import rw.ac.auca.gigcampus.repository.GigRepository;
import rw.ac.auca.gigcampus.repository.StudentRepository;

@Service
public class GigService {
    private final GigRepository gigs;
    private final EmployerRepository employers;
    private final StudentRepository students;

public GigService(GigRepository gigs, EmployerRepository employers, StudentRepository students) {
        this.gigs = gigs;
        this.employers = employers;
        this.students = students;
    }
public List<Gig> all() {
        return gigs.findAll();
    }
public Optional<Gig> one(UUID id) {
        return gigs.findById(id);
    }
@Transactional
public String save(Gig input) {
        Student student = resolveStudent(input.getStudent());
        if (student == null) {
            return "The student with that id does not exist";
        }
        Employer employer = resolveEmployer(input.getEmployer());
        if (employer == null) {
            return "The employer with that id does not exist";
        }
        Gig gig = new Gig();
        gig.setGigDate(input.getGigDate());
        gig.setStatus(input.getStatus());
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
        Student student = resolveStudent(input.getStudent());
        if (student == null) {
            return "The student with that id does not exist";
        }
        Employer employer = resolveEmployer(input.getEmployer());
        if (employer == null) {
            return "The employer with that id does not exist";
        }
        Gig gig = found.get();
        gig.setGigDate(input.getGigDate());
        gig.setStatus(input.getStatus());
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
        gigs.delete(found.get());
        return "Gig deleted successfully";
    }
    private Student resolveStudent(Student input) {
        if (input == null || input.getId() == null) {
            return null;
        }
        return students.findById(input.getId()).orElse(null);
    }
    private Employer resolveEmployer(Employer input) {
        if (input == null || input.getId() == null) {
            return null;
        }
        return employers.findById(input.getId()).orElse(null);
}
}