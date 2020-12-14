package fr.chaffotm.gamebook.elementary;

import fr.chaffotm.gamebook.elementary.api.GameAPI;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class GameResourceIT {

    private GameAPI gameAPI;

    @BeforeEach
    public void setUp() {
        gameAPI = new GameAPI();
    }

    @Test
    @DisplayName("It should play the game")
    public void playGame() {
        gameAPI.stopGame();

        final JsonPath startGame = gameAPI.startGame(1);
        assertThat(startGame.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"startGame\": {\n" +
                        "            \"section\": {\n" +
                        "                \"paragraphs\": [\n" +
                        "                    \"You are locked in a room.\",\n" +
                        "                    \"The only visible exit is the door.\"\n" +
                        "                ],\n" +
                        "                \"actions\": [\n" +
                        "                    {\n" +
                        "                        \"description\": \"If you want to open the door\",\n" +
                        "                        \"id\": 254\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"description\": \"Or inspect the shelves\",\n" +
                        "                        \"id\": 191\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");

        final JsonPath getGame = gameAPI.getGame();
        assertThat(getGame.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"game\": {\n" +
                        "            \"section\": {\n" +
                        "                \"reference\": 0,\n" +
                        "                \"paragraphs\": [\n" +
                        "                    \"You are locked in a room.\",\n" +
                        "                    \"The only visible exit is the door.\"\n" +
                        "                ],\n" +
                        "                \"actions\": [\n" +
                        "                    {\n" +
                        "                        \"id\": 254,\n" +
                        "                        \"description\": \"If you want to open the door\"\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"id\": 191,\n" +
                        "                        \"description\": \"Or inspect the shelves\"\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");

        final JsonPath turnTo = gameAPI.turnTo(254);
        assertThat(turnTo.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"turnTo\": {\n" +
                        "            \"section\": {\n" +
                        "                \"reference\": 254,\n" +
                        "                \"paragraphs\": [\n" +
                        "                    \"The door is locked. You tried to break down the door.\"\n" +
                        "                ],\n" +
                        "                \"actions\": [\n" +
                        "                    {\n" +
                        "                        \"id\": 12,\n" +
                        "                        \"description\": null\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
    }

    @Test
    @DisplayName("It should play the whole game")
    public void playTheWholeGame() {
        gameAPI.stopGame();
        JsonPath response = gameAPI.startGame(1);

        assertThat(response.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"startGame\": {\n" +
                        "            \"section\": {\n" +
                        "                \"paragraphs\": [\n" +
                        "                    \"You are locked in a room.\",\n" +
                        "                    \"The only visible exit is the door.\"\n" +
                        "                ],\n" +
                        "                \"actions\": [\n" +
                        "                    {\n" +
                        "                        \"description\": \"If you want to open the door\",\n" +
                        "                        \"id\": 254\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"description\": \"Or inspect the shelves\",\n" +
                        "                        \"id\": 191\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
        response = gameAPI.turnTo(254);
        assertThat(response.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"turnTo\": {\n" +
                        "            \"section\": {\n" +
                        "                \"reference\": 254,\n" +
                        "                \"paragraphs\": [\n" +
                        "                    \"The door is locked. You tried to break down the door.\"\n" +
                        "                ],\n" +
                        "                \"actions\": [\n" +
                        "                    {\n" +
                        "                        \"id\": 12,\n" +
                        "                        \"description\": null\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
        response = gameAPI.turnTo(12);
        assertThat(response.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"turnTo\": {\n" +
                        "            \"section\": {\n" +
                        "                \"reference\": 12,\n" +
                        "                \"paragraphs\": [\n" +
                        "                    \"You cannot open the door.\"\n" +
                        "                ],\n" +
                        "                \"actions\": [\n" +
                        "                    {\n" +
                        "                        \"id\": 191,\n" +
                        "                        \"description\": null\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
        response = gameAPI.turnTo(191);
        assertThat(response.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"turnTo\": {\n" +
                        "            \"section\": {\n" +
                        "                \"reference\": 191,\n" +
                        "                \"paragraphs\": [\n" +
                        "                    \"Between two books, you find a map showing how to open the door.\"\n" +
                        "                ],\n" +
                        "                \"actions\": [\n" +
                        "                    {\n" +
                        "                        \"id\": 254,\n" +
                        "                        \"description\": null\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
        response = gameAPI.turnTo(254);
        assertThat(response.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"turnTo\": {\n" +
                        "            \"section\": {\n" +
                        "                \"reference\": 254,\n" +
                        "                \"paragraphs\": [\n" +
                        "                    \"The door is locked. You tried to break down the door.\"\n" +
                        "                ],\n" +
                        "                \"actions\": [\n" +
                        "                    {\n" +
                        "                        \"id\": 10,\n" +
                        "                        \"description\": null\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                        \"id\": 12,\n" +
                        "                        \"description\": null\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
        response = gameAPI.turnTo(10);
        assertThat(response.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"turnTo\": {\n" +
                        "            \"section\": {\n" +
                        "                \"reference\": 10,\n" +
                        "                \"paragraphs\": [\n" +
                        "                    \"You are out.\"\n" +
                        "                ],\n" +
                        "                \"actions\": [\n" +
                        "                    \n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
    }

}
