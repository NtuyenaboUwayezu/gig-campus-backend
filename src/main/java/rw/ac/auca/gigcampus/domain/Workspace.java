package rw.ac.auca.gigcampus.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "workspace")
public class Workspace {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
@Column(name = "workspace_number")
    private int workspaceNumber;
@Column(name = "name")
    private String name;
public UUID getId() {
        return id;
    }
public void setId(UUID id) {
        this.id = id;
}
public int getWorkspaceNumber() {
        return workspaceNumber;
}
public void setWorkspaceNumber(int workspaceNumber) {
        this.workspaceNumber = workspaceNumber;
}
public String getName() {
        return name;
}
public void setName(String name) {
        this.name = name;
}
}
