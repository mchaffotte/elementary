package fr.chaffotm.gamebook.elementary;

import fr.chaffotm.gamebook.elementary.api.GameAPI;
import fr.chaffotm.gamebook.elementary.model.resource.*;
import fr.chaffotm.gamebook.elementary.model.resource.Character;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class GameResourceIT {

    private GameAPI gameAPI;

    @BeforeEach
    public void setUp() {
        gameAPI = new GameAPI();
    }

    private Character getCharacter() {
        final Skill athletics = new Skill("athletics", 1);
        final Skill intuition = new Skill("intuition", 1);

        final Character character = new Character();
        character.setName("John Doe");
        character.setSkills(Set.of(athletics, intuition));
        return character;
    }

    private Game getPrologue() {
        final Section section = new Section();
        section.setReference(0);
        section.setText("![trap](trap.png)\n\nYou are locked in a room.\n\nThe only visible exit is the door.");
        section.setActions(List.of(
                new Action(254, "If you want to open the door"),
                new Action(191, "Or inspect the shelves")
        ));
        final Game game = new Game();
        game.setSection(section);
        game.setCharacter(getCharacter());
        return game;
    }

    private Game getSection254() {
        final Section section = new Section();
        section.setReference(254);
        section.setText("The door is locked. You tried to break down the door.");
        section.setActions(List.of(
                new Action(12, "")
        ));
        final Game game = new Game();
        game.setSection(section);
        game.setCharacter(getCharacter());
        return game;
    }

    @Test
    @DisplayName("It should play the game")
    public void playGame() {
        final boolean stop = gameAPI.stopGame();
        assertThat(stop).isTrue();

        Game game = gameAPI.startGame(1);
        assertThat(game).isEqualTo(getPrologue());

        game = gameAPI.getGame();
        assertThat(game).isEqualTo(getPrologue());

        game = gameAPI.turnTo(254);
        assertThat(game).isEqualTo(getSection254());
    }

    @Test
    @DisplayName("It should play the whole game")
    public void playTheWholeGame() {
        gameAPI.stopGame();

        Game game = gameAPI.startGame(1);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action(254, "If you want to open the door"),
                        new Action(191, "Or inspect the shelves"));

        game = gameAPI.turnTo(254);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action(12, ""));
        assertThat(game.getCharacter().getSkills())
                .containsOnly(
                        new Skill("athletics", 1),
                        new Skill("intuition", 1));

        game = gameAPI.turnTo(12);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action(191, ""));
        assertThat(game.getCharacter().getSkills())
                .containsOnly(
                        new Skill("athletics", -2),
                        new Skill("intuition", 1));

        game = gameAPI.turnTo(191);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action(254, ""));

        game = gameAPI.turnTo(254);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action(10, ""));

        game = gameAPI.turnTo(10);
        assertThat(game.getSection().getActions())
                .isEmpty();
    }

}
