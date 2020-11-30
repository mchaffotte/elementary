package fr.chaffotm.gamebook.elementary.model.entity.instance;

import fr.chaffotm.gamebook.elementary.model.entity.definition.CharacterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SkillDefinition;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "character_instance")
public class CharacterInstance {

    @Id
    @SequenceGenerator(name = "characterInstanceSeq", sequenceName = "character_instance_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "characterInstanceSeq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fk_character_instance_game_instance"))
    private GameInstance game;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "character",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<SkillInstance> skills = new HashSet<>();

    public CharacterInstance() {
        // used by JPA
    }

    public CharacterInstance(final CharacterDefinition character) {
        name = character.getName();
        skills = new HashSet<>();
        for (SkillDefinition skill : character.getSkills()) {
            addSkill(new SkillInstance(skill));
        }
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public GameInstance getGame() {
        return game;
    }

    public void setGame(GameInstance game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SkillInstance> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillInstance> skills) {
        this.skills = skills;
    }

    public void addSkill(final SkillInstance skill) {
        skills.add(skill);
        skill.setCharacter(this);
    }

}
