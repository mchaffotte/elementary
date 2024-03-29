package fr.chaffotm.gamebook.elementary.model.resource;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Character {

    private String name;

    private Set<Skill> skills = new HashSet<>();

    private Money money;

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

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(name, character.name) && Objects.equals(skills, character.skills) && Objects.equals(money, character.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, skills, money);
    }
}
