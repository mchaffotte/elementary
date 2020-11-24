package fr.chaffotm.gamebook.elementary.model.entity;

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
public class SectionEntity {

    @Id
    @SequenceGenerator(name = "sectionSeq", sequenceName = "section_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "sectionSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", foreignKey = @ForeignKey(name = "fx_section_story"))
    private StoryEntity story;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prologue_story_id", foreignKey = @ForeignKey(name = "fx_section_prologue_story"))
    private StoryEntity prologueStory;

    private int reference;

    @Column(name = "content", nullable = false)
    @ElementCollection
    @CollectionTable(name = "paragraph",
            joinColumns = @JoinColumn(name = "section_id"),
            foreignKey = @ForeignKey(name = "fk_section_paragraph")
    )
    private List<String> paragraphs = new ArrayList<>();

    @OneToMany(
            mappedBy = "section",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EventEntity> events = new ArrayList<>();

    private ActionSelection selection;

    @OneToMany(
            mappedBy = "section",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ActionEntity> actions = new ArrayList<>();

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

    public StoryEntity getPrologueStory() {
        return prologueStory;
    }

    public void setPrologueStory(StoryEntity prologueStory) {
        this.prologueStory = prologueStory;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public void addParagraph(String paragraph) {
        paragraphs.add(paragraph);
    }

    public List<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(List<EventEntity> events) {
        this.events = events;
    }

    public void addEvent(final EventEntity event) {
        events.add(event);
        event.setSection(this);
    }

    public ActionSelection getSelection() {
        return selection;
    }

    public void setSelection(ActionSelection selection) {
        this.selection = selection;
    }

    public List<ActionEntity> getActions() {
        return actions;
    }

    public void setActions(List<ActionEntity> actions) {
        this.actions = actions;
    }

    public void addAction(final ActionEntity action) {
        actions.add(action);
        action.setSection(this);
    }
}
