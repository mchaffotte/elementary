package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    @SequenceGenerator(name = "eventSeq", sequenceName = "event_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "eventSeq")
    private Long id;

    @Column(nullable = false)
    private String type;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "fk_event_parameter_id"))
    private Set<ParameterEntity> parameters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
