package rw.ac.auca.gigcampus.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
@Column(name = "first_name")
    private String firstName;
@Column(name = "last_name")
    private String lastName;
@Column(name = "reg_number")
    private String regNumber;
@Column(name = "email")
    private String email;
@ManyToOne
@JoinColumn(name = "workspace_id")
    private Workspace workspace;
@ManyToMany
@JoinTable(name = "student_skill", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills = new ArrayList<>();
public UUID getId() {
        return id;
}
public void setId(UUID id) {
        this.id = id;
}
public String getFirstName() {
        return firstName;
}
public void setFirstName(String firstName) {
        this.firstName = firstName;
}
public String getLastName() {
        return lastName;
}
public void setLastName(String lastName) {
        this.lastName = lastName;
}
public String getRegNumber() {
        return regNumber;
}
public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
}
public String getEmail() {
        return email;
}
public void setEmail(String email) {
        this.email = email;
}
public Workspace getWorkspace() {
        return workspace;
}
public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
}
public List<Skill> getSkills() {
        return skills;
}
public void setSkills(List<Skill> skills) {
        this.skills = skills;
}
}
