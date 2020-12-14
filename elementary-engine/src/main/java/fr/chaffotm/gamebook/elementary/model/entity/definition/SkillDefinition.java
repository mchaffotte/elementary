package fr.chaffotm.gamebook.elementary.model.entity.definition;

import fr.chaffotm.gamebook.elementary.model.entity.ReadOnlyEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Skill")
@Table(name = "skill")
@EntityListeners(ReadOnlyEntityListener.class)
public class SkillDefinition {

    @Id
    @SequenceGenerator(name = "skillSeq", sequenceName = "skill_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "skillSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", foreignKey = @ForeignKey(name = "fk_skill_character"))
    private CharacterDefinition character;

    @Column(nullable = false)
    private String name;

    private int value;

    public SkillDefinition() {
        // used by JPA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CharacterDefinition getCharacter() {
        return character;
    }

    public void setCharacter(CharacterDefinition character) {
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
        SkillDefinition skill = (SkillDefinition) o;
        return value == skill.value &&
                Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
