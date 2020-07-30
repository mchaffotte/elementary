package fr.chaffotm.gamebook.elementary.repository;

import fr.chaffotm.gamebook.elementary.model.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.SectionDefinition;
import fr.chaffotm.gamebook.elementary.model.StoryDefinition;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class StoryDefinitionRepository {

    private final StoryDefinition definition;

    public StoryDefinitionRepository() {
        definition = buildDefault();
    }

    private ActionDefinition build(String description, int nextSectionId) {
        ActionDefinition action = new ActionDefinition();
        action.setDescription(description);
        action.setNextSectionId(nextSectionId);
        return action;
    }

    private StoryDefinition buildDefault() {
        SectionDefinition analyzeArrow = new SectionDefinition();
        analyzeArrow.setId(254);
        analyzeArrow.setParagraphs(
                List.of(
                        "\"Stay on your seat\" said the flight attendant."
                )
        );
        analyzeArrow.setActions(List.of());

        final SectionDefinition prologue = new SectionDefinition();
        prologue.setParagraphs(
                List.of(
                        "You are on the long-haul flight 78455 to Papeete.",
                        "It's dark outside. Almost all of the passengers are sleeping.",
                        "It is at this moment that a cry is heard at the front of the plane."
                )
        );
        prologue.setActions(
                List.of(
                        build("If you want to call the flight attendant", 254),
                        build("Or to rush to the front of the plane", 191)
                )
        );

        final StoryDefinition storyDefinition = new StoryDefinition();
        storyDefinition.setName("Murder at 20,000 Feet");
        storyDefinition.setPrologue(prologue);
        storyDefinition.setSections(
                List.of(
                        analyzeArrow
                )
        );
        return storyDefinition;
    }

    public StoryDefinition getStoryDefinition() {
        return definition;
    }

}
