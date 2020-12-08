package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.entity.definition.StoryDefinition;
import fr.chaffotm.gamebook.elementary.model.resource.Story;

import java.util.List;
import java.util.stream.Collectors;

public class StoryMapper {

    public static Story map(final StoryDefinition entity) {
        final Story story = new Story();
        story.setId(entity.getId());
        story.setName(entity.getName());
        return story;
    }

    public static List<Story> map(List<StoryDefinition> stories) {
        return stories.stream()
                .map(StoryMapper::map)
                .collect(Collectors.toList());
    }
}
