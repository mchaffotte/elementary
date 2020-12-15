package fr.chaffotm.gamebook.elementary.api;

import fr.chaffotm.gamebook.elementary.model.resource.Game;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class GameAPI {

    public Game getGame() {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"query getGame {\\n  game {\\n    section {\\n      reference\\n      paragraphs\\n      actions {\\n        id\\n        description\\n      }\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":\"getGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.game", Game.class);
    }

    public Game startGame(final long storyId) {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"mutation startGame { startGame(storyId: " + storyId + ") { section{ paragraphs\\n actions { description\\n id}}}}\",\"variables\":null,\"operationName\":\"startGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.startGame", Game.class);
    }

    public Game turnTo(final int nextReference) {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"mutation turnTo { turnTo(nextReference: " + nextReference + ") {section { reference\\n paragraphs\\n  actions {  id\\n  description}}}}\",\"variables\":null,\"operationName\":\"turnTo\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.turnTo", Game.class);
    }

    public boolean stopGame() {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"mutation stopGame { stopGame }\",\"variables\":null,\"operationName\":\"stopGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.stopGame", boolean.class);
    }
}
