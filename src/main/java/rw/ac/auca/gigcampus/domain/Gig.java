package rw.ac.auca.gigcampus.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "gig")
public class Gig {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
@Column(name = "gig_date")
    private LocalDate gigDate;
@Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GigStatus status;
    @Column(name = "title")
    private String title;
    @Column(name = "budget")
    private double budget;
@ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;
@ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
public UUID getId() {
        return id;
}
public void setId(UUID id) {
        this.id = id;
}
public LocalDate getGigDate() {
        return gigDate;
}
public void setGigDate(LocalDate gigDate) {
        this.gigDate = gigDate;
}
public GigStatus getStatus() {
        return status;
}
public void setStatus(GigStatus status) {
        this.status = status;
}
public String getTitle() {
        return title;
}
public void setTitle(String title) {
        this.title = title;
}
public double getBudget() {
        return budget;
}
public void setBudget(double budget) {
        this.budget = budget;
}
public Employer getEmployer() {
        return employer;
}
public void setEmployer(Employer employer) {
        this.employer = employer;
}
public Student getStudent() {
        return student;
}
public void setStudent(Student student) {
        this.student = student;
}
}
