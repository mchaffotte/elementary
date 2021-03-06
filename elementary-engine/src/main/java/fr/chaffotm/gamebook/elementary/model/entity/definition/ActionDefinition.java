package fr.chaffotm.gamebook.elementary.model.entity.definition;

import fr.chaffotm.gamebook.elementary.model.entity.ReadOnlyEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Action")
@Table(name = "action")
@EntityListeners(ReadOnlyEntityListener.class)
public class ActionDefinition {

    @Id
    @SequenceGenerator(name = "actionSeq", sequenceName = "action_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "actionSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_action_section"))
    private SectionDefinition section;

    private String expression;

    private Evaluation evaluation;

    @OneToMany(
            mappedBy = "action",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OptionDefinition> options = new ArrayList<>();

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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public List<OptionDefinition> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDefinition> options) {
        this.options = options;
    }

    public void addOption(final OptionDefinition option) {
        options.add(option);
        option.setAction(this);
    }
}
