package rw.ac.auca.gigcampus.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.ac.auca.gigcampus.domain.Employer;

public interface EmployerRepository
        extends JpaRepository<Employer, UUID> {

    List<Employer>
    findByOrganizationIgnoreCaseOrderByLastNameAsc(
            String organization);

            @Query("""
    SELECT DISTINCT g.employer
    FROM Gig g
    WHERE g.student.id = :studentId
    """)
List<Employer> findEmployersOfStudent(
        @Param("studentId") UUID studentId);
        
}
