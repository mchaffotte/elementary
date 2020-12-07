package fr.chaffotm.gamebook.elementary.model.io;

import fr.chaffotm.gamebook.elementary.model.builder.ActionDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;

public class IOSingleAction implements IOAction {

    private String description;

    private int toReference;

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

    @Override
    public ActionDefinition toActionDefinition() {
        return new ActionDefinitionBuilder(description, toReference).build();
    }
}
