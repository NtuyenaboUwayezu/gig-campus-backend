package rw.ac.auca.gigcampus.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.gigcampus.domain.Skill;
import rw.ac.auca.gigcampus.domain.Student;
import rw.ac.auca.gigcampus.repository.SkillRepository;

@Service
public class SkillService {

    private final SkillRepository skills;

    public SkillService(SkillRepository skills) {
        this.skills = skills;
    }

    public List<Skill> all() {
        return skills.findAll();
    }

    public Optional<Skill> one(UUID id) {
        return skills.findById(id);
    }

    @Transactional
    public String save(Skill skill) {

        skills.save(skill);

        return "Skill saved successfully";
    }

    @Transactional
    public String update(UUID id, Skill input) {

        Optional<Skill> found = skills.findById(id);

        if (found.isEmpty()) {
            return "The skill with that id does not exist";
        }

        Skill skill = found.get();

        skill.setName(input.getName());

        skills.save(skill);

        return "Skill updated successfully";
    }

    @Transactional
    public String delete(UUID id) {

        Optional<Skill> found = skills.findById(id);

        if (found.isEmpty()) {
            return "The skill with that id does not exist";
        }

        skills.delete(found.get());

        return "Skill deleted successfully";
    }

public List<Skill> unusedSkills() {
    return skills.findUnusedSkills();
}

}