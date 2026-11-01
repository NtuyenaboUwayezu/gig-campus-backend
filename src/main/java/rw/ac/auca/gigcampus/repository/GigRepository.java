package rw.ac.auca.gigcampus.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.gigcampus.domain.Gig;
import rw.ac.auca.gigcampus.domain.GigStatus;

public interface GigRepository extends JpaRepository<Gig, UUID> {

    List<Gig> findByStatusOrderByGigDateAsc(GigStatus status);

    List<Gig> findByGigDateBetweenOrderByGigDateAsc(LocalDate start, LocalDate end);

    boolean existsByStudent_IdAndGigDateAndStatusNot(UUID studentId, LocalDate gigDate, GigStatus status);

    @Query("""
        SELECT g.status, COUNT(g)
        FROM Gig g
        GROUP BY g.status
        """)
    List<Object[]> countGigsByStatus();

    @Modifying
    @Transactional
    @Query("""
        UPDATE Gig g
        SET g.status =
        rw.ac.auca.gigcampus.domain.GigStatus.WITHDRAWN
        WHERE g.student.id = :studentId
        AND g.gigDate = :date
        AND g.status <>
        rw.ac.auca.gigcampus.domain.GigStatus.DELIVERED
        """)
    int cancelStudentDay(
            @Param("studentId") UUID studentId,
            @Param("date") LocalDate date);
}