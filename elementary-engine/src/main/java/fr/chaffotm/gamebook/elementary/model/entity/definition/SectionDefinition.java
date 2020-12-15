package fr.chaffotm.gamebook.elementary.model.entity.definition;

import fr.chaffotm.gamebook.elementary.model.entity.ReadOnlyEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Section")
@Table(name = "section",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_story_section", columnNames = {"story_id", "reference"}),
            @UniqueConstraint(name = "uk_prologue_story_section", columnNames = {"prologue_story_id", "reference"})
        })
@NamedQuery(name = "getSection",
        query = "SELECT s FROM Section s WHERE s.story=:story AND s.reference=:reference")
@EntityListeners(ReadOnlyEntityListener.class)
public class SectionDefinition {

    @Id
    @SequenceGenerator(name = "sectionSeq", sequenceName = "section_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "sectionSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", foreignKey = @ForeignKey(name = "fx_section_story"))
    private StoryDefinition story;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prologue_story_id", foreignKey = @ForeignKey(name = "fx_section_prologue_story"))
    private StoryDefinition prologueStory;

    private int reference;

    @Lob
    @Column(name="text", length=1024)
    private String text;

    @OneToMany(
            mappedBy = "section",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EventDefinition> events = new ArrayList<>();

    @OneToMany(
            mappedBy = "section",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ActionDefinition> actions = new ArrayList<>();

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

    public StoryDefinition getPrologueStory() {
        return prologueStory;
    }

    public void setPrologueStory(StoryDefinition prologueStory) {
        this.prologueStory = prologueStory;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<EventDefinition> getEvents() {
        return events;
    }

    public void setEvents(List<EventDefinition> events) {
        this.events = events;
    }

    public void addEvent(final EventDefinition event) {
        events.add(event);
        event.setSection(this);
    }

    public List<ActionDefinition> getActions() {
        return actions;
    }

    public void setActions(List<ActionDefinition> actions) {
        this.actions = actions;
    }

    public void addAction(final ActionDefinition action) {
        actions.add(action);
        action.setSection(this);
    }
}
