package fr.chaffotm.gamebook.elementary.repository;

import fr.chaffotm.gamebook.elementary.model.instance.GameInstance;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameRepository {

    private GameInstance game;

    public GameInstance getGame() {
        return game;
    }

    public void removeGame() {
        game = null;
    }

    public GameInstance save(final GameInstance game) {
        this.game = game;
        return game;
    }
}
