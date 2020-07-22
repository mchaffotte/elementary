package fr.chaffotm.gamebook.elementary;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class StoryResourceIT {

    @Test
    @DisplayName("It should return the story")
    public void getStory() {
        given()
            .when()
                .body("{\"query\":\"query getStory {\\n  story {\\n    name\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"getStory\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .body(is("{\"data\":{\"story\":{\"name\":\"Murder at the Opera\"}}}"));
    }
}
