package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "action")
public class ActionEntity {

    @Id
    @SequenceGenerator(name = "actionSeq", sequenceName = "action_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "actionSeq")
    private Long id;

    private String expression;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OptionEntity> options = new ArrayList<>();

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

    public List<OptionEntity> getOptions() {
        return options;
    }

    public void setOptions(List<OptionEntity> options) {
        this.options = options;
    }

    public void addOption(final OptionEntity option) {
        options.add(option);
    }
}
