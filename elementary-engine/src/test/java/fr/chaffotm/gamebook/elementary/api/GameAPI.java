package fr.chaffotm.gamebook.elementary.api;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class GameAPI {

    public JsonPath getGame() {
        return given()
            .when()
                .body("{\"query\":\"query getGame {\\n  game {\\n    section {\\n      reference\\n      paragraphs\\n      actions {\\n        id\\n        description\\n      }\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":\"getGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
    }

    public JsonPath startGame(final long storyId) {
        return given()
            .when()
                .body("{\"query\":\"mutation startGame { startGame(storyId: " + storyId + ") { section{ paragraphs\\n actions { description\\n id}}}}\",\"variables\":null,\"operationName\":\"startGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
    }

    public JsonPath turnTo(final int nextReference) {
        return given()
            .when()
                .body("{\"query\":\"mutation turnTo { turnTo(nextReference: " + nextReference + ") {section { reference\\n paragraphs\\n  actions {  id\\n  description}}}}\",\"variables\":null,\"operationName\":\"turnTo\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
    }

    public void stopGame() {
        given()
        .when()
            .body("{\"query\":\"mutation stopGame{stopGame}\",\"variables\":null,\"operationName\":\"stopGame\"}")
            .post("/graphql")
        .then()
            .statusCode(200)
            .body(is("{\"data\":{\"stopGame\":true}}"));
    }
}
