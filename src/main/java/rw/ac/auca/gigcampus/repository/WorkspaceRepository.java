package rw.ac.auca.gigcampus.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.ac.auca.gigcampus.domain.Workspace;

public interface WorkspaceRepository
        extends JpaRepository<Workspace, UUID> {

    @Query("""
        SELECT w
        FROM Workspace w
        WHERE NOT EXISTS (
            SELECT s
            FROM Student s
            WHERE s.workspace = w
        )
        ORDER BY w.workspaceNumber ASC
        """)
    List<Workspace> findFreeWorkspaces();
    Optional<Workspace> findByWorkspaceNumber(int workspaceNumber);
    
}