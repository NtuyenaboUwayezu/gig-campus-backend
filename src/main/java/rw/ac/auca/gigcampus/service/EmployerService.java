package rw.ac.auca.gigcampus.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.gigcampus.domain.Employer;
import rw.ac.auca.gigcampus.repository.EmployerRepository;
import rw.ac.auca.gigcampus.repository.StudentRepository;

@Service
public class EmployerService {

    private final EmployerRepository employers;

    private final StudentRepository students;

  public EmployerService(EmployerRepository employers, StudentRepository students) {

    this.employers = employers;
    this.students = students;
}

    public List<Employer> all() {
        return employers.findAll();
    }

    public Optional<Employer> one(UUID id) {
        return employers.findById(id);
    }

    @Transactional
    public String save(Employer employer) {

        employers.save(employer);

        return "Employer saved successfully";
    }

    @Transactional
    public String update(UUID id, Employer input) {

        Optional<Employer> found = employers.findById(id);

        if (found.isEmpty()) {
            return "The employer with that id does not exist";
        }

        Employer employer = found.get();

        employer.setFirstName(input.getFirstName());
        employer.setLastName(input.getLastName());
        employer.setPhone(input.getPhone());
        employer.setOrganization(input.getOrganization());

        employers.save(employer);

        return "Employer updated successfully";
    }

    @Transactional
    public String delete(UUID id) {

        Optional<Employer> found = employers.findById(id);

        if (found.isEmpty()) {
            return "The employer with that id does not exist";
        }

        employers.delete(found.get());

        return "Employer deleted successfully";
    }

    public List<Employer> byOrganization(String organization) {
    return employers
            .findByOrganizationIgnoreCaseOrderByLastNameAsc(
                    organization);
}

public List<Employer> ofStudent(UUID studentId) {

    if (!students.existsById(studentId)) {
        return null;
    }

    return employers.findEmployersOfStudent(studentId);
}

}