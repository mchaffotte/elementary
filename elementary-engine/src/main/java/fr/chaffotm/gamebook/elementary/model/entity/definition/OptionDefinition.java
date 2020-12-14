package fr.chaffotm.gamebook.elementary.model.entity.definition;

import fr.chaffotm.gamebook.elementary.model.entity.ReadOnlyEntityListener;

import javax.persistence.*;

@Entity(name = "Option")
@Table(name = "option")
@EntityListeners(ReadOnlyEntityListener.class)
public class OptionDefinition {

    @Id
    @SequenceGenerator(name = "optionSeq", sequenceName = "option_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "optionSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", foreignKey = @ForeignKey(name = "fk_option_action"))
    private ActionDefinition action;

    private String expression;

    private String description;

    @Column(name = "next_reference", nullable = false)
    private int nextReference;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "story_id", foreignKey = @ForeignKey(name = "fk_option_event"))
    private EventDefinition event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActionDefinition getAction() {
        return action;
    }

    public void setAction(ActionDefinition action) {
        this.action = action;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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
