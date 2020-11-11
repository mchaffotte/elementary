package fr.chaffotm.gamebook.elementary.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "parameter")
public class ParameterEntity {

    @Id
    @SequenceGenerator(name = "parameterSeq", sequenceName = "parameter_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "parameterSeq")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String value;

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
