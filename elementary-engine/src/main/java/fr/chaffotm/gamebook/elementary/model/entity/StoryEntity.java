package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "story",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_story_character", columnNames = {"character_id"}),
                @UniqueConstraint(name = "uk_story_prologue", columnNames = {"prologue_id"})
        })
public class StoryEntity {

    @Id
    @SequenceGenerator(name = "storySeq", sequenceName = "story_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "storySeq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_story_character"))
    private CharacterEntity character;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_story_prologue"))
    private SectionEntity prologue;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable(name = "section_story",
            joinColumns=@JoinColumn(name="story_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id"),
            uniqueConstraints = @UniqueConstraint(name="uk_story_section", columnNames = {"section_id"}),
            foreignKey = @ForeignKey(name = "fk_section_story_id"),
            inverseForeignKey = @ForeignKey(name = "fk_story_section_id")
    )
    private Set<SectionEntity> sections = new HashSet<>();

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

    public CharacterEntity getCharacter() {
        return character;
    }

    public void setCharacter(CharacterEntity character) {
        character.setStory(this);
        this.character = character;
    }

    public SectionEntity getPrologue() {
        return prologue;
    }

    public void setPrologue(SectionEntity prologue) {
        prologue.setStory(this);
        this.prologue = prologue;
    }

    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }

    public void addSection(SectionEntity section) {
        sections.add(section);
        section.setStory(this);
    }

}
