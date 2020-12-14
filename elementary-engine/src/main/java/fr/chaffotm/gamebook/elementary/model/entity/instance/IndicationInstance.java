package fr.chaffotm.gamebook.elementary.model.entity.instance;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "indication_instance")
public class IndicationInstance {

    @Id
    @SequenceGenerator(name = "indicationInstanceSeq", sequenceName = "indication_instance_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "indicationInstanceSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fk_indication_instance_game_instance"))
    private GameInstance game;

    private IndicationType type;

    private String value;

    public IndicationInstance() {
        // Used bu JPA
    }

    public IndicationInstance(final IndicationType type, final String value) {
        this.type = type;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameInstance getGame() {
        return game;
    }

    public void setGame(GameInstance game) {
        this.game = game;
    }

    public IndicationType getType() {
        return type;
    }

    public void setType(IndicationType type) {
        this.type = type;
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
        IndicationInstance that = (IndicationInstance) o;
        return type == that.type &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return "IndicationInstance{" +
                "id=" + id +
                ", type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
