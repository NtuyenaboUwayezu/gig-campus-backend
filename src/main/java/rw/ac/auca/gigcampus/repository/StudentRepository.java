package rw.ac.auca.gigcampus.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.ac.auca.gigcampus.domain.Student;
import rw.ac.auca.gigcampus.domain.Workspace;

public interface StudentRepository
        extends JpaRepository<Student, UUID> {

    @Query("""
        SELECT s
        FROM Student s
        JOIN s.skills sk
        WHERE LOWER(sk.name)
        LIKE LOWER(CONCAT('%', :name, '%'))
        """)
    List<Student> findBySkillName(
            @Param("name") String name);
    boolean existsByWorkspace(Workspace workspace);
}