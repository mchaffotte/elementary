package fr.chaffotm.gamebook.elementary.model.entity.instance;

import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.OptionDefinition;

import javax.persistence.*;

@Entity
@Table(name = "action_instance")
public class ActionInstance {

    @Id
    @SequenceGenerator(name = "actionInstanceSeq", sequenceName = "action_instance_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "actionInstanceSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_action_instance_section_instance"))
    private SectionInstance section;

    private String description;

    private int nextReference;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "fx_action_instance_event"))
    private EventDefinition event;

    public ActionInstance() {
        // Used bu JPA
    }

    public ActionInstance(final OptionDefinition option) {
        this(option.getNextReference(), option.getDescription(), option.getEvent());
    }

    public ActionInstance(final int nextReference, final String description, final EventDefinition event) {
        this.nextReference = nextReference;
        this.description = description == null ? "" : description.trim();
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SectionInstance getSection() {
        return section;
    }

    public void setSection(SectionInstance section) {
        this.section = section;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNextReference() {
        return nextReference;
    }

    public void setNextReference(int nextReference) {
        this.nextReference = nextReference;
    }

    public EventDefinition getEvent() {
        return event;
    }

    public void setEvent(EventDefinition event) {
        this.event = event;
    }
}
