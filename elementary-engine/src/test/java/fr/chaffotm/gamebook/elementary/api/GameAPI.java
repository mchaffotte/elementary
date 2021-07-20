package fr.chaffotm.gamebook.elementary.api;

import fr.chaffotm.gamebook.elementary.model.resource.Game;
import fr.chaffotm.gamebook.elementary.model.resource.Indication;
import io.restassured.path.json.JsonPath;

import java.util.StringJoiner;

import static io.restassured.RestAssured.given;

public class GameAPI {

    public Game getGame() {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"query getGame {\\n  game {\\n    section {\\n      storyId\\n      reference\\n      text\\n      actions {\\n        description\\n      }\\n    }\\n    character {\\n      name\\n      money{\\n pounds\\n shillings\\n pence\\n }\\n      skills {\\n        name\\n        value\\n      }\\n    }\\n  }\\n}\\n\",\"variables\":{},\"operationName\":\"getGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.game", Game.class);
    }

    public Game startGame(final long storyId) {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"mutation startGame {\\n startGame(storyId: " + storyId + ") {\\n section {\\n storyId\\n reference\\n text\\n actions {\\n description\\n}\\n}\\n character {\\n name\\n money{\\n pounds\\n shillings\\n pence\\n }\\n skills {\\n name\\n value\\n }\\n}\\n}\\n}\\n\",\"variables\":{},\"operationName\":\"startGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.startGame", Game.class);
    }

    public Game turnTo(final int index) {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"mutation turnTo {\\n  turnTo(index: " + index + ") {\\n    section {\\n      storyId\\n      reference\\n      text\\n      actions {\\n        description\\n      answerNeeded\\n      }\\n    }\\n    character {\\n      name\\n      money{\\n pounds\\n shillings\\n pence\\n }\\n      skills {\\n        name\\n        value\\n      }\\n    }\\n  }\\n}\\n\",\"variables\":{},\"operationName\":\"turnTo\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.turnTo", Game.class);
    }

    public Game turnTo(final int index, String answer) {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"mutation turnTo($answer: String) {\\n  turnTo(index: " + index + ", answer: $answer) {\\n    section {\\n      storyId\\n      reference\\n      text\\n      actions {\\n        description\\n      answerNeeded\\n      }\\n    }\\n    character {\\n      name\\n      money{\\n pounds\\n shillings\\n pence\\n }\\n      skills {\\n        name\\n        value\\n      }\\n    }\\n  }\\n}\\n\",\"variables\":{\"answer\":\"" + answer + "\"},\"operationName\":\"turnTo\"}")
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

    public Game startFrom(final int storyId, final int reference, final Indication... indications) {
        final StringJoiner indicationJoiner = new StringJoiner(", ", "[", "]");
        for (Indication indication : indications) {
            indicationJoiner.add("{\"name\": \"" + indication.getName() + "\", \"value\": \"" + indication.getValue() + "\"}");
        }
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"mutation startFrom($indications: [IndicationInput]) {\\n  startFrom(storyId: " + storyId + ", reference: " + reference + ", indications: $indications) {\\n    section {\\n      storyId\\n      reference\\n      text\\n      actions {\\n        description\\n      }\\n    }\\n  }\\n}\\n\",\"variables\":{\"indications\":" +  indicationJoiner + "},\"operationName\":\"startFrom\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.startFrom", Game.class);
    }
}
