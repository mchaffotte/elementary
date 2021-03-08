package fr.chaffotm.gamebook.elementary.model.entity.definition;

import fr.chaffotm.gamebook.elementary.model.entity.ReadOnlyEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Story")
@Table(name = "story",
        uniqueConstraints = @UniqueConstraint(name = "uk_story_name", columnNames = "name"))
@NamedQueries({
        @NamedQuery(name = "getStories", query = "SELECT s FROM Story s"),
        @NamedQuery(name = "getStory", query = "SELECT s FROM Story s WHERE s.id=:id")
})
@EntityListeners(ReadOnlyEntityListener.class)
public class StoryDefinition {

    @Id
    @SequenceGenerator(name = "storySeq", sequenceName = "story_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "storySeq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "prologueStory", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private SectionDefinition prologue;

    private String location;

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

    public SectionDefinition getPrologue() {
        return prologue;
    }

    public void setPrologue(SectionDefinition prologue) {
        this.prologue = prologue;
        prologue.setPrologueStory(this);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryDefinition that = (StoryDefinition) o;
        return Objects.equals(name, that.name);
    }
}
