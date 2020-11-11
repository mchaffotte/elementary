package fr.chaffotm.gamebook.elementary;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GameResourceIT {

    @Test
    @DisplayName("It should play the game")
    public void playGame() {
        given()
            .when()
                .body("{\"query\":\"mutation stopGame {\\n  stopGame\\n}\",\"variables\":null,\"operationName\":\"stopGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .body(is("{\"data\":{\"stopGame\":true}}"));

        given()
            .when()
                .body("{\"query\":\"mutation startGame {\\n  startGame {\\n    section {\\n      id\\n      paragraphs\\n      actions {\\n        id\\n        description\\n      }\\n    }\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"startGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .body(is("{\"data\":{\"startGame\":{\"section\":{\"id\":0,\"paragraphs\":[\"You are locked in a room.\",\"The only visible exit is the door.\"],\"actions\":[{\"id\":254,\"description\":\"If you want to open the door\"},{\"id\":191,\"description\":\"Or inspect the shelves\"}]}}}}"));

        given()
            .when()
                .body("{\"query\":\"query getGame {\\n  game {\\n    section {\\n      id\\n      paragraphs\\n      actions {\\n        id\\n        description\\n      }\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":\"getGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .body(is("{\"data\":{\"game\":{\"section\":{\"id\":0,\"paragraphs\":[\"You are locked in a room.\",\"The only visible exit is the door.\"],\"actions\":[{\"id\":254,\"description\":\"If you want to open the door\"},{\"id\":191,\"description\":\"Or inspect the shelves\"}]}}}}"));

        given()
            .when()
                .body("{\"query\":\"mutation turnTo {\\n  turnTo(nextReference: 254) {\\n    section {\\n      id\\n      paragraphs\\n    actions {\\n        id\\n        description\\n      }\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":\"turnTo\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .body(is("{\"data\":{\"turnTo\":{\"section\":{\"id\":254,\"paragraphs\":[\"The door is locked. You tried to break down the door.\"],\"actions\":[{\"id\":12,\"description\":null}]}}}}"));
    }
}
