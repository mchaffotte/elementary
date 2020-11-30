package fr.chaffotm.gamebook.elementary.model.entity.instance;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "section_instance")
public class SectionInstance {

    @Id
    @SequenceGenerator(name = "sectionInstanceSeq", sequenceName = "section_instance_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "sectionInstanceSeq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private GameInstance game;

    private int reference;

    @Column(name = "content", nullable = false)
    @ElementCollection
    @CollectionTable(name = "paragraph_instance",
            joinColumns = @JoinColumn(name = "section_id"),
            foreignKey = @ForeignKey(name = "fk_section_instance_paragraph")
    )
    private List<String> paragraphs = new ArrayList<>();

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

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
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
