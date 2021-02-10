package fr.chaffotm.gamebook.elementary.model.io;

import fr.chaffotm.gamebook.elementary.model.builder.ActionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.builder.OptionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;

public class IOSingleAction implements IOAction {

    private String description;

    private int toReference;

    private IOEvent event;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getToReference() {
        return toReference;
    }

    public void setToReference(int toReference) {
        this.toReference = toReference;
    }

    public IOEvent getEvent() {
        return event;
    }

    public void setEvent(IOEvent event) {
        this.event = event;
    }

    @Override
    public ActionDefinition toActionDefinition() {
        final OptionDefinitionBuilder optionBuilder = new OptionDefinitionBuilder(toReference)
                .description(this.description);
        if (event != null) {
            optionBuilder.event(event.toEventDefinition());
        }
        return new ActionDefinitionBuilder(optionBuilder.build()).build();
    }
}
