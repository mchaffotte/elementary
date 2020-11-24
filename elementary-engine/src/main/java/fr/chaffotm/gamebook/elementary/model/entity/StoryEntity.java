package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Story")
@Table(name = "story",
        uniqueConstraints = @UniqueConstraint(name = "uk_story_name", columnNames = "name"))
@EntityListeners(ReadOnlyEntityListener.class)
public class StoryEntity {

    @Id
    @SequenceGenerator(name = "storySeq", sequenceName = "story_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "storySeq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "prologueStory", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private SectionEntity prologue;

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

    public SectionEntity getPrologue() {
        return prologue;
    }

    public void setPrologue(SectionEntity prologue) {
        this.prologue = prologue;
        prologue.setPrologueStory(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryEntity that = (StoryEntity) o;
        return Objects.equals(name, that.name);
    }
}
