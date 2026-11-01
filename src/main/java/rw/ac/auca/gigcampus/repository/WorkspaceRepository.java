package rw.ac.auca.gigcampus.repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.gigcampus.domain.Workspace;
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
}