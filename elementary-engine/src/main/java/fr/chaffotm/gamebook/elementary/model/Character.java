package fr.chaffotm.gamebook.elementary.model;

import java.util.Optional;
import java.util.Set;

public class Character {

    private String name;

    private Set<Skill> skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Optional<Skill> getSkill(final String name) {
        return skills.stream()
                .filter(skill -> skill.getName().equals(name))
                .findFirst();
    }
}
