package fr.chaffotm.gamebook.elementary;

import fr.chaffotm.gamebook.elementary.api.StoryAPI;
import fr.chaffotm.gamebook.elementary.model.resource.Story;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        final Story story = new Story();
        story.setId(1);
        story.setName("Trapped");
        final List<Story> stories = storyAPI.getStories();
        assertThat(stories).containsOnly(story);

        final Story firstStory = storyAPI.getStory(stories.get(0).getId());
        assertThat(firstStory).isEqualTo(story);
    }

}
