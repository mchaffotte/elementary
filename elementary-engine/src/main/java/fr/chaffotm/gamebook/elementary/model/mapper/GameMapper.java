package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.instance.GameInstance;
import fr.chaffotm.gamebook.elementary.model.resource.Game;

public class GameMapper {

    public static Game map(final GameInstance gameInstance) {
        final Game game = new Game();
        game.setSection(SectionMapper.map(gameInstance.getSection()));
        return game;
    }

}
