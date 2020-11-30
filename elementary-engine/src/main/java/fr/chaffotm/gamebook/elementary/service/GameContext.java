package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.entity.instance.CharacterInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationInstance;

import java.util.Set;

public class GameContext {

    private final Die die;

    private final GameInstance game;

    public GameContext(final Die die, final GameInstance game) {
        this.die = die;
        this.game = game;
    }

    public Die getDie() {
        return die;
    }

    public void addIndication(final IndicationInstance indication) {
        game.addIndication(indication);
    }

    public Set<IndicationInstance> getIndications() {
        return game.getIndications();
    }

    public CharacterInstance getCharacter() {
        return game.getCharacter();
    }
}
