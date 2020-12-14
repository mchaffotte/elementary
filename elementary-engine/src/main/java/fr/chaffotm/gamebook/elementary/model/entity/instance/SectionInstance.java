package fr.chaffotm.gamebook.elementary.model.entity.instance;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "section_instance",
        uniqueConstraints = @UniqueConstraint(name = "uk_section_instance_game_instance", columnNames = {"game_id"} )
)
public class SectionInstance {

    @Id
    @SequenceGenerator(name = "sectionInstanceSeq", sequenceName = "section_instance_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "sectionInstanceSeq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fx_section_instance_game_instance"))
    private GameInstance game;

    private int reference;

    @Lob
    @Column(name="text", length=1024)
    private String text;

    @OneToMany(
            mappedBy = "section",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ActionInstance> actions = new ArrayList<>();

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

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ActionInstance> getActions() {
        return actions;
    }

    public void setActions(List<ActionInstance> actions) {
        this.actions = actions;
    }

    public void addAction(ActionInstance action) {
        this.actions.add(action);
        action.setSection(this);
    }
}
