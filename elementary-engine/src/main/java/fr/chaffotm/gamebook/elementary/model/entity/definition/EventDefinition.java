package fr.chaffotm.gamebook.elementary.model.entity.definition;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Event")
@Table(name = "event")
public class EventDefinition {

    @Id
    @SequenceGenerator(name = "eventSeq", sequenceName = "event_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "eventSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_event_section_id"))
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
    }
}
