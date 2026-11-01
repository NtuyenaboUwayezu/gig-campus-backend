package rw.ac.auca.gigcampus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.gigcampus.domain.Skill;
import rw.ac.auca.gigcampus.domain.Student;
import rw.ac.auca.gigcampus.domain.Workspace;
import rw.ac.auca.gigcampus.repository.SkillRepository;
import rw.ac.auca.gigcampus.repository.StudentRepository;
import rw.ac.auca.gigcampus.repository.WorkspaceRepository;

@Service
public class StudentService {
    private final StudentRepository students;
    private final WorkspaceRepository workspaces;
    private final SkillRepository skills;
public StudentService(StudentRepository students, WorkspaceRepository workspaces, SkillRepository skills) {
        this.students = students;
        this.workspaces = workspaces;
        this.skills = skills;
    }
public List<Student> all() {
        return students.findAll();
    }
public Optional<Student> one(UUID id) {
        return students.findById(id);
    }
@Transactional
    public String save(Student input) {
        Student student = new Student();
        student.setFirstName(input.getFirstName());
        student.setLastName(input.getLastName());
        student.setRegNumber(input.getRegNumber());
        student.setEmail(input.getEmail());
        student.setWorkspace(resolveWorkspace(input.getWorkspace()));
        student.setSkills(resolveSkills(input.getSkills()));
        students.save(student);
        return "Student saved successfully";
    }
@Transactional
    public String update(UUID id, Student input) {
        Optional<Student> found = students.findById(id);
        if (found.isEmpty()) {
            return "The student with that id does not exist";
        }
        Student student = found.get();
        student.setFirstName(input.getFirstName());
        student.setLastName(input.getLastName());
        student.setRegNumber(input.getRegNumber());
        student.setEmail(input.getEmail());
        student.setWorkspace(resolveWorkspace(input.getWorkspace()));
        student.setSkills(resolveSkills(input.getSkills()));
        students.save(student);
        return "Student updated successfully";
    }
@Transactional
    public String delete(UUID id) {
        Optional<Student> found = students.findById(id);
        if (found.isEmpty()) {
            return "The student with that id does not exist";
        }
        Student student = found.get();
        student.getSkills().clear();
        students.save(student);
        students.delete(student);
        return "Student deleted successfully";
    }
    private Workspace resolveWorkspace(Workspace input) {
        if (input == null || input.getId() == null) {
            return null;
        }
        return workspaces.findById(input.getId()).orElse(null);
    }
    private List<Skill> resolveSkills(List<Skill> inputSkills) {
        List<Skill> selected = new ArrayList<>();
        if (inputSkills == null) {
            return selected;
        }
        for (Skill inputSkill : inputSkills) {
            if (inputSkill.getId() != null) {
                Optional<Skill> found =
                        skills.findById(inputSkill.getId());
                if (found.isPresent()) {
                    selected.add(found.get());
                }
            }
        }
        return selected;
}

public List<Student> bySkill(String name) {
    return students.findBySkillName(name);
}

@Transactional
public String addSkill(UUID studentId, UUID skillId) {

    Optional<Student> studentFound = students.findById(studentId);

    if (studentFound.isEmpty()) {
        return "The student with that id does not exist";
    }

    Optional<Skill> skillFound = skills.findById(skillId);

    if (skillFound.isEmpty()) {
        return "That skill does not exist";
    }

    Student student = studentFound.get();
    Skill skill = skillFound.get();

    if (student.getSkills().contains(skill)) {
        return "The student already has that skill";
    }

    student.getSkills().add(skill);
    students.save(student);

    return "Skill added to the student";
}

@Transactional
public String changeWorkspace(
        UUID studentId,
        int workspaceNumber) {

    Optional<Student> studentFound =
            students.findById(studentId);

    if (studentFound.isEmpty()) {
        return "The student with that id does not exist";
    }

    Optional<Workspace> workspaceFound =
            workspaces.findByWorkspaceNumber(
                    workspaceNumber);

    if (workspaceFound.isEmpty()) {
        return "The workspace with that workspaceNumber does not exist";
    }

    Workspace workspace = workspaceFound.get();

    if (students.existsByWorkspace(workspace)) {
        return "That workspace is taken by another student";
    }

    Student student = studentFound.get();

    student.setWorkspace(workspace);
    students.save(student);

    return "Workspace changed successfully";
}

}