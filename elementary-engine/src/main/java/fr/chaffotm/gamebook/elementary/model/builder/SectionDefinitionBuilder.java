package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionSelection;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;

public class SectionDefinitionBuilder {

    private final SectionDefinition section;

    public SectionDefinitionBuilder(int reference) {
        section = new SectionDefinition();
        section.setReference(reference);
    }

    public SectionDefinition build() {
        return section;
    }

    public SectionDefinitionBuilder paragraph(final String paragraph) {
        section.addParagraph(paragraph);
        return this;
    }

    public SectionDefinitionBuilder event(final EventDefinition event) {
        section.addEvent(event);
        return this;
    }

    public SectionDefinitionBuilder selection(final ActionSelection selection) {
        section.setSelection(selection);
        return this;
    }

    public SectionDefinitionBuilder action(final ActionDefinition action) {
        section.addAction(action);
        return this;
    }
}
