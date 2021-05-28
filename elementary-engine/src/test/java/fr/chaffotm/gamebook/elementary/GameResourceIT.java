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

    private Game getPrologue(final long storyId) {
        final Section section = new Section();
        section.setStoryId(storyId);
        section.setReference(0);
        section.setText("![trap](trap.png)\n\nYou are locked in a room.\n\nThe only visible exit is the door.");
        section.setActions(List.of(
                new Action("If you want to open the door", false),
                new Action("Or inspect the shelves", false)
        ));
        final Game game = new Game();
        game.setSection(section);
        game.setCharacter(getCharacter());
        return game;
    }

    private Game getSection254(final long storyId) {
        final Section section = new Section();
        section.setStoryId(storyId);
        section.setReference(254);
        section.setText("The door is locked. You tried to break down the door.");
        section.setActions(List.of(
                new Action("", false)
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
        assertThat(game).isEqualTo(getPrologue(1));

        game = gameAPI.getGame();
        assertThat(game).isEqualTo(getPrologue(1));

        game = gameAPI.turnTo(0);
        assertThat(game).isEqualTo(getSection254(1));
    }

    @Test
    @DisplayName("It should play the whole game")
    public void playTheWholeGame() {
        gameAPI.stopGame();

        Game game = gameAPI.startGame(1);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action("If you want to open the door", false),
                        new Action("Or inspect the shelves", false));

        game = gameAPI.turnTo(0);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action("", false));
        assertThat(game.getCharacter().getSkills())
                .containsOnly(
                        new Skill("athletics", 1),
                        new Skill("intuition", 1));

        game = gameAPI.turnTo(0);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action("", false));
        assertThat(game.getCharacter().getSkills())
                .containsOnly(
                        new Skill("athletics", -2),
                        new Skill("intuition", 1));

        game = gameAPI.turnTo(0);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action("", false));

        game = gameAPI.turnTo(0);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action("", false));

        game = gameAPI.turnTo(0);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action("", true));

        game = gameAPI.turnTo(0, "shoe");
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action("", true));

        game = gameAPI.turnTo(0, "sox");
        assertThat(game.getSection().getActions())
                .isEmpty();
    }

    @Test
    @DisplayName("It should start the game from the given reference")
    public void startTheGameFromTheGivenReference() {
        gameAPI.stopGame();

        Game game = gameAPI.startFrom(1, 254);
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action("", false));

        gameAPI.stopGame();

        game = gameAPI.startFrom(1, 254, new Indication("clue", "A"), new Indication("decision", "1"));
        assertThat(game.getSection().getActions())
                .containsOnly(
                        new Action("", false));
    }

}
