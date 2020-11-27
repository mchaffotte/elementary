package fr.chaffotm.gamebook.elementary.model.entity.definition;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity(name = "Character")
@Table(name = "character")
public class CharacterDefinition {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "story_id", foreignKey = @ForeignKey(name = "fx_character_story"))
    private StoryDefinition story;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "character",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<SkillDefinition> skills = new HashSet<>();

    public CharacterDefinition() {
        // used by JPA
    }

    public CharacterDefinition(final CharacterDefinition character) {
        name = character.name;
        skills = new HashSet<>();
        for (SkillDefinition skill : character.skills) {
            skills.add(new SkillDefinition(skill));
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoryDefinition getStory() {
        return story;
    }

    public void setStory(StoryDefinition story) {
        this.story = story;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SkillDefinition> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDefinition> skills) {
        this.skills = skills;
    }

    public void addSkill(final SkillDefinition skill) {
        skills.add(skill);
        skill.setCharacter(this);
    }

    public Optional<SkillDefinition> getSkill(final String name) {
        return skills.stream()
                .filter(skill -> skill.getName().equals(name))
                .findFirst();
    }
}
