package fr.chaffotm.gamebook.elementary.model.entity.instance;

import fr.chaffotm.gamebook.elementary.model.entity.definition.SkillDefinition;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "skill_instance")
public class SkillInstance {

    @Id
    @SequenceGenerator(name = "skillInstanceSeq", sequenceName = "skill_instance_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "skillInstanceSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", foreignKey = @ForeignKey(name = "fk_skill_instance_character_instance"))
    private CharacterInstance character;

    @Column(nullable = false)
    private String name;

    private int value;

    public SkillInstance() {
        // used by JPA
    }

    public SkillInstance(final SkillDefinition skill) {
        name = skill.getName();
        value = skill.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CharacterInstance getCharacter() {
        return character;
    }

    public void setCharacter(CharacterInstance character) {
        this.character = character;
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
        SkillInstance skill = (SkillInstance) o;
        return value == skill.value &&
                Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
