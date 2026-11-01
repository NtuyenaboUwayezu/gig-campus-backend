package rw.ac.auca.gigcampus.repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.gigcampus.domain.Student;
public interface StudentRepository extends JpaRepository<Student, UUID> {
}