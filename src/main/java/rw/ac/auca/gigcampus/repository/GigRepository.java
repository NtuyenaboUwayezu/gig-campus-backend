package rw.ac.auca.gigcampus.repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.gigcampus.domain.Gig;
public interface GigRepository extends JpaRepository<Gig, UUID> {
}