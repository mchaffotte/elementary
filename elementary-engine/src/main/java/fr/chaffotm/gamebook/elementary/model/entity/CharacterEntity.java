package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "character")
public class CharacterEntity {

    @Id
    @SequenceGenerator(name = "characterSeq", sequenceName = "character_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "characterSeq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", foreignKey = @ForeignKey(name = "fk_character_story"))
    private StoryEntity story;

    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "character_id", foreignKey = @ForeignKey(name = "fk_character_skill_id"))
    private Set<SkillEntity> skills = new HashSet<>();

    public CharacterEntity() {
        // used by JPA
    }

    public CharacterEntity(final CharacterEntity character) {
        name = character.name;
        skills = new HashSet<>();
        for (SkillEntity skill : character.skills) {
            skills.add(new SkillEntity(skill));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoryEntity getStory() {
        return story;
    }

    public void setStory(StoryEntity story) {
        this.story = story;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillEntity> skills) {
        this.skills = skills;
    }

    public Optional<SkillEntity> getSkill(final String name) {
        return skills.stream()
                .filter(skill -> skill.getName().equals(name))
                .findFirst();
    }

    public void addSkill(final SkillEntity skill) {
        skills.add(skill);
    }
}
