package fr.chaffotm.gamebook.elementary.model.entity.definition;

import fr.chaffotm.gamebook.elementary.model.entity.ReadOnlyEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Parameter")
@Table(name = "parameter")
@EntityListeners(ReadOnlyEntityListener.class)
public class ParameterDefinition {

    @Id
    @SequenceGenerator(name = "parameterSeq", sequenceName = "parameter_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "parameterSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "fk_parameter_event_id"))
    private EventDefinition event;

    @Column(nullable = false)
    private String name;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventDefinition getEvent() {
        return event;
    }

    public void setEvent(EventDefinition event) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterDefinition that = (ParameterDefinition) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
