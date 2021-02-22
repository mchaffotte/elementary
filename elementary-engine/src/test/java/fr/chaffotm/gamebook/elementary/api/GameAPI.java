package fr.chaffotm.gamebook.elementary.api;

import fr.chaffotm.gamebook.elementary.model.resource.Game;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class GameAPI {

    public Game getGame() {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"query getGame {\\n  game {\\n    section {\\n      reference\\n      paragraphs\\n      actions {\\n        id\\n        description\\n      }\\n    }\\n    character {\\n      name\\n      skills {\\n        name\\n        value\\n      }\\n    }\\n  }\\n}\\n\",\"variables\":{},\"operationName\":\"getGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.game", Game.class);
    }

    public Game startGame(final long storyId) {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"mutation startGame {\\n startGame(storyId: " + storyId + ") {\\n section {\\n reference\\n paragraphs\\n actions {\\n id\\n description\\n}\\n}\\n character {\\n name\\n skills {\\n name\\n value\\n }\\n}\\n}\\n}\\n\",\"variables\":{},\"operationName\":\"startGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.startGame", Game.class);
    }

    public Game turnTo(final int nextReference) {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"mutation turnTo {\\n  turnTo(nextReference: " + nextReference + ") {\\n    section {\\n      reference\\n      paragraphs\\n      actions {\\n        id\\n        description\\n      }\\n    }\\n    character {\\n      name\\n      skills {\\n        name\\n        value\\n      }\\n    }\\n  }\\n}\\n\",\"variables\":{},\"operationName\":\"turnTo\"}")
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
