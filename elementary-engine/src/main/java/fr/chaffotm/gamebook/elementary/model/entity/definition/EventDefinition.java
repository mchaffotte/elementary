package fr.chaffotm.gamebook.elementary.model.entity.definition;

import fr.chaffotm.gamebook.elementary.model.entity.ReadOnlyEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Event")
@Table(name = "event")
@EntityListeners(ReadOnlyEntityListener.class)
public class EventDefinition {

    @Id
    @SequenceGenerator(name = "eventSeq", sequenceName = "event_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "eventSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_event_section"))
    private SectionDefinition section;

    @Column(nullable = false)
    private String type;

    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ParameterDefinition> parameters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SectionDefinition getSection() {
        return section;
    }

    public void setSection(SectionDefinition section) {
        this.section = section;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<ParameterDefinition> getParameters() {
        return parameters;
    }

    public void setParameters(Set<ParameterDefinition> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(final ParameterDefinition parameter) {
        parameters.add(parameter);
        parameter.setEvent(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDefinition that = (EventDefinition) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, parameters);
    }
}
