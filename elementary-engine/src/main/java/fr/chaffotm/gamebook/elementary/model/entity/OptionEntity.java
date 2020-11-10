package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;

@Entity
public class OptionEntity {

    @Id
    @SequenceGenerator(name = "optionSeq", sequenceName = "option_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "optionSeq")
    private Long id;

    private String expression;

    private String description;

    private int nextReference;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "story_id", foreignKey = @ForeignKey(name = "fk_option_event"))
    private EventEntity event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }
}
