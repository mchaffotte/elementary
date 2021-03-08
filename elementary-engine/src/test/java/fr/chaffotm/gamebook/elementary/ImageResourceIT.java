package fr.chaffotm.gamebook.elementary;

import fr.chaffotm.gamebook.elementary.api.StoryAPI;
import fr.chaffotm.gamebook.elementary.model.resource.Story;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
@QuarkusTest
public class ImageResourceIT {

    private StoryAPI storyAPI;

    @BeforeEach
    public void setUp() {
        storyAPI = new StoryAPI();
    }

    @Test
    @DisplayName("It should return the image")
    public void getImage() {
        final Story story = new Story();
        story.setId(1);
        story.setName("Trapped");
        final List<Story> stories = storyAPI.getStories();
        assertThat(stories).containsOnly(story);

        given()
            .when().get("/stories/" + story.getId() + "/images/trap.png")
            .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("It should not found the image if it is a unknown one")
    public void getUnknownImage() {
        final Story story = new Story();
        story.setId(1);
        story.setName("Trapped");
        final List<Story> stories = storyAPI.getStories();
        assertThat(stories).containsOnly(story);

        given()
                .when().get("/stories/" + story.getId() + "/images/unknown.png")
                .then()
                .statusCode(404);
    }

}
