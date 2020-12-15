package fr.chaffotm.gamebook.elementary.api;

import fr.chaffotm.gamebook.elementary.model.resource.Story;
import io.restassured.path.json.JsonPath;

import java.util.List;

import static io.restassured.RestAssured.given;

public class StoryAPI {

    public List<Story> getStories() {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"query getStories { stories(offset: 0, limit: 10) { id\\n name }}\",\"variables\":null,\"operationName\":\"getStories\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getList("data.stories", Story.class);
    }

    public Story getStory(final long id) {
        final JsonPath jsonPath = given()
            .when()
                .body("{\"query\":\"query getStory { story(storyId: " + id + ") { id,\\n name }}\",\"variables\":null,\"operationName\":\"getStory\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
        return jsonPath.getObject("data.story", Story.class);
    }
}
