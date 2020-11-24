package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;

@Entity(name = "Parameter")
@Table(name = "parameter")
public class ParameterEntity {

    @Id
    @SequenceGenerator(name = "parameterSeq", sequenceName = "parameter_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "parameterSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "fk_parameter_event_id"))
    private EventEntity event;

    @Column(nullable = false)
    private String name;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
