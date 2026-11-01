package rw.ac.auca.gigcampus.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.ac.auca.gigcampus.domain.Employer;

public interface EmployerRepository
        extends JpaRepository<Employer, UUID> {

    List<Employer>
    findByOrganizationIgnoreCaseOrderByLastNameAsc(
            String organization);
}