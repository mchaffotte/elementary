package fr.chaffotm.gamebook.elementary.model.entity.instance;

import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game_instance")
@NamedQuery(name = "getGame", query = "SELECT g FROM GameInstance g")
public class GameInstance {

    @Id
    @SequenceGenerator(name = "gameInstanceSeq", sequenceName = "game_instance_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "gameInstanceSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "story_id", foreignKey = @ForeignKey(name = "fk_game_instance_story"))
    private StoryDefinition story;

    @OneToOne(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private SectionInstance section;

    @OneToOne(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private CharacterInstance character;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IndicationInstance> indications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoryDefinition getStory() {
        return story;
    }

    public void setStory(StoryDefinition story) {
        this.story = story;
    }

    public SectionInstance getSection() {
        return section;
    }

    public void setSection(SectionInstance section) {
        this.section = section;
        section.setGame(this);
    }

    public CharacterInstance getCharacter() {
        return character;
    }

    public void setCharacter(CharacterInstance character) {
        this.character = character;
        character.setGame(this);
    }

    public Set<IndicationInstance> getIndications() {
        return indications;
    }

    public void setIndications(Set<IndicationInstance> indications) {
        this.indications = indications;
    }

    public void addIndication(final IndicationInstance indication) {
        indications.add(indication);
        indication.setGame(this);
    }
}
