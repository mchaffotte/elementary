package fr.chaffotm.gamebook.elementary.api;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class StoryAPI {

    public JsonPath getStories() {
        return given()
            .when()
                .body("{\"query\":\"query getStories { stories(offset: 0, limit: 10) { id}}\",\"variables\":null,\"operationName\":\"getStories\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
    }

    public JsonPath getStory(final long id) {
        return given()
            .when()
                .body("{\"query\":\"query getStory { story(storyId: " + id + ") { id,\\n  name }}\",\"variables\":null,\"operationName\":\"getStory\"}")
                .post("/graphql")
            .then()
                .statusCode(200)
                .extract().body().jsonPath();
    }
}
