package rw.ac.auca.gigcampus.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.gigcampus.domain.Gig;
import rw.ac.auca.gigcampus.domain.GigStatus;
public interface GigRepository extends JpaRepository<Gig, UUID> {
    List<Gig> findByStatusOrderByGigDateAsc(GigStatus status);
    List<Gig> findByGigDateBetweenOrderByGigDateAsc(LocalDate start, LocalDate end);
    boolean existsByStudent_IdAndGigDateAndStatusNot(UUID studentId, LocalDate gigDate, GigStatus status);
    
}

