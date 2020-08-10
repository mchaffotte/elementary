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
                .body("{\"query\":\"mutation startGame {\\n  startGame {\\n    section {\\n      id\\n      paragraphs\\n      actions {\\n        id\\n        description\\n      }\\n    }\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"startGame\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .body(is("{\"data\":{\"startGame\":{\"section\":{\"id\":0,\"paragraphs\":[\"You are on the long-haul flight 78455 to Papeete.\",\"It's dark outside. Almost all of the passengers are sleeping.\",\"It is at this moment that a cry is heard at the front of the plane.\"],\"actions\":[{\"id\":254,\"description\":\"If you want to call the flight attendant\"},{\"id\":191,\"description\":\"Or to rush to the front of the plane\"}]}}}}"));

        given()
            .when()
                .body("{\"query\":\"mutation turnTo {\\n  turnTo(sectionId: 254) {\\n    section {\\n      id\\n      paragraphs\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":\"turnTo\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .body(is("{\"data\":{\"turnTo\":{\"section\":{\"id\":254,\"paragraphs\":[\"\\\"Stay on your seat\\\" said the flight attendant.\"]}}}}"));
    }
}
