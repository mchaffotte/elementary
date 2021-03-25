package fr.chaffotm.gamebook.elementary.model.builder;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.definition.SectionDefinition;

public class SectionDefinitionBuilder {

    private final SectionDefinition section;

    private final StringBuilder textBuilder;

    public SectionDefinitionBuilder(final int reference) {
        textBuilder = new StringBuilder();
        section = new SectionDefinition();
        section.setReference(reference);
    }

    public SectionDefinition build() {
        section.setText(textBuilder.toString());
        return section;
    }

    public SectionDefinitionBuilder paragraph(final String paragraph) {
        if (textBuilder.length() > 0) {
            textBuilder.append("\n\n");
        }
        textBuilder.append(paragraph);
        return this;
    }

    public SectionDefinitionBuilder event(final EventDefinition event) {
        section.addEvent(event);
        return this;
    }

    public SectionDefinitionBuilder action(final ActionDefinition action) {
        section.addAction(action);
        return this;
    }
}
