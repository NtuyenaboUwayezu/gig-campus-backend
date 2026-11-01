package rw.ac.auca.gigcampus.repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.gigcampus.domain.Skill;
public interface SkillRepository extends JpaRepository<Skill, UUID> {
}