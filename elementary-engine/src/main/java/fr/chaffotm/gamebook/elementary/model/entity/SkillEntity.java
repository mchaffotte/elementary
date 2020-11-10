package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "skill")
public class SkillEntity {

    @Id
    @SequenceGenerator(name = "skillSeq", sequenceName = "skill_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "skillSeq")
    private Long id;

    private String name;

    private int value;

    public SkillEntity() {
        // used by JPA
    }

    public SkillEntity(final SkillEntity skill) {
        name = skill.name;
        value = skill.value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillEntity skill = (SkillEntity) o;
        return value == skill.value &&
                Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
