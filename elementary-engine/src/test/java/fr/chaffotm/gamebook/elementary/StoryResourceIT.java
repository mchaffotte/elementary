package fr.chaffotm.gamebook.elementary;

import fr.chaffotm.gamebook.elementary.api.StoryAPI;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class StoryResourceIT {

    private StoryAPI storyAPI;

    @BeforeEach
    public void setUp() {
        storyAPI = new StoryAPI();
    }

    @Test
    @DisplayName("It should display the content of the default story")
    public void getStories() {
        final JsonPath getStories = storyAPI.getStories();
        assertThat(getStories.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"stories\": [\n" +
                        "            {\n" +
                        "                \"id\": 1\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}");

        final JsonPath getStory = storyAPI.getStory(1);
        assertThat(getStory.prettify())
                .isEqualTo("{\n" +
                        "    \"data\": {\n" +
                        "        \"story\": {\n" +
                        "            \"id\": 1,\n" +
                        "            \"name\": \"Trapped\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
    }
}
