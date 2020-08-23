package fr.chaffotm.gamebook.elementary.model.definition;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Character {

    private String name;

    private Set<Skill> skills;

    public Character() {
        skills = new HashSet<>();
    }

    public Character(final Character character) {
        name = character.name;
        skills = new HashSet<>();
        for (Skill skill : character.skills) {
            skills.add(new Skill(skill));
        }
    }

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
