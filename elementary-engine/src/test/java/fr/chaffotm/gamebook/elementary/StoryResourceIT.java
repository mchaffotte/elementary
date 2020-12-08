package fr.chaffotm.gamebook.elementary;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class StoryResourceIT {

    @Test
    @DisplayName("It should display the content of the default story")
    public void getStories() {
        given()
                .when()
                .body("{\"query\":\"query getStories {\\n  stories(offset: 0, limit: 10) {\\n    id\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"getStories\"}")
                .post("/graphql")
                .then()
                .statusCode(200)
                .body(is("{\"data\":{\"stories\":[{\"id\":1}]}}"));

        given()
                .when()
                .body("{\"query\":\"query getStory {\\n  story(storyId: 1) {\\n    id,\\n    name\\n  }\\n}\\n\",\"variables\":null,\"operationName\":\"getStory\"}")
                .post("/graphql")
                .then()
                .statusCode(200)
                .body(is("{\"data\":{\"story\":{\"id\":1,\"name\":\"Trapped\"}}}"));
    }
}
