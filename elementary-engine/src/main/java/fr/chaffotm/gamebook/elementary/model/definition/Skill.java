package fr.chaffotm.gamebook.elementary.model.definition;

import java.util.Objects;

public class Skill {

    private String name;

    private int value;

    public Skill(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return value == skill.value &&
                Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
