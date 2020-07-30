package fr.chaffotm.gamebook.elementary.model.mapper;

import fr.chaffotm.gamebook.elementary.model.resource.Action;
import fr.chaffotm.gamebook.elementary.model.resource.Section;
import fr.chaffotm.gamebook.elementary.model.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.SectionDefinition;

import java.util.ArrayList;
import java.util.List;

public class SectionMapper {

    public static Section map(final SectionDefinition sectionDefinition) {
        final Section section = new Section();
        section.setId(sectionDefinition.getId());
        section.setParagraphs(sectionDefinition.getParagraphs());
        final List<Action> actions = new ArrayList<>();
        for (ActionDefinition choice : sectionDefinition.getActions()) {
            actions.add(new Action(choice.getDescription(), choice.getNextSectionId()));
        }
        section.setActions(actions);
        return section;
    }

}
