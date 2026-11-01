package rw.ac.auca.gigcampus.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.ac.auca.gigcampus.domain.Skill;

public interface SkillRepository
        extends JpaRepository<Skill, UUID> {

    @Query("""
        SELECT s
        FROM Skill s
        WHERE s.students IS EMPTY
        """)
    List<Skill> findUnusedSkills();
}
