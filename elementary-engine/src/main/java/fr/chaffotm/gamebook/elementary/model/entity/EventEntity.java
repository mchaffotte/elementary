package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Event")
@Table(name = "event")
public class EventEntity {

    @Id
    @SequenceGenerator(name = "eventSeq", sequenceName = "event_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "eventSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_event_section_id"))
    private SectionEntity section;

    @Column(nullable = false)
    private String type;

    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ParameterEntity> parameters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SectionEntity getSection() {
        return section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<ParameterEntity> getParameters() {
        return parameters;
    }

    public void setParameters(Set<ParameterEntity> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(final ParameterEntity parameter) {
        parameters.add(parameter);
    }
}
