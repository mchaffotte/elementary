package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.entity.StoryEntity;
import fr.chaffotm.gamebook.elementary.model.resource.Story;

public class StoryMapper {

    public static Story map(final StoryEntity entity) {
        final Story story = new Story();
        story.setName(entity.getName());
        return story;
    }

}
