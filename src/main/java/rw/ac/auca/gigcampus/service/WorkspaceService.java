package rw.ac.auca.gigcampus.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.gigcampus.domain.Workspace;
import rw.ac.auca.gigcampus.repository.WorkspaceRepository;

@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaces;
public WorkspaceService(WorkspaceRepository workspaces) {
        this.workspaces = workspaces;
}
public List<Workspace> all() {
        return workspaces.findAll();
}
public Optional<Workspace> one(UUID id) {
        return workspaces.findById(id);
}
@Transactional
    public String save(Workspace workspace) {
        workspaces.save(workspace);
        return "Workspace saved successfully";
    }
@Transactional
public String update(UUID id, Workspace input) {
        Optional<Workspace> found = workspaces.findById(id);
        if (found.isEmpty()) {
            return "The workspace with that id does not exist";
        }
        Workspace workspace = found.get();
        workspace.setWorkspaceNumber(input.getWorkspaceNumber());
        workspace.setName(input.getName());
        workspaces.save(workspace);
        return "Workspace updated successfully";
    }
@Transactional
    public String delete(UUID id) {
        Optional<Workspace> found = workspaces.findById(id);
        if (found.isEmpty()) {
            return "The workspace with that id does not exist";
        }
        workspaces.delete(found.get());
        return "Workspace deleted successfully";
}
}