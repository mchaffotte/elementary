package fr.chaffotm.gamebook.elementary.assertion;

import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

public class ActionInstanceAssert extends AbstractAssert<ActionInstanceAssert, ActionInstance> {

    public static ActionInstanceAssert assertThat(ActionInstance actual) {
        return new ActionInstanceAssert(actual);
    }

    public ActionInstanceAssert(ActionInstance actual) {
        super(actual, ActionInstanceAssert.class);
    }

    public ActionInstanceAssert hasNextReference(final int nextReference) {
        isNotNull();
        if (actual.getNextReference() != nextReference) {
            failWithMessage("Expected action to have nextReference %s but was %s",
                    nextReference, actual.getNextReference());
        }
        return this;
    }

    public ActionInstanceAssert hasDescription(final String description) {
        isNotNull();
        if (!Objects.equals(actual.getDescription(), description)) {
            failWithMessage("Expected action to have description %s but was %s",
                    description, actual.getDescription());
        }
        return this;
    }

    public ActionInstanceAssert hasNoDescription() {
        isNotNull();
        if (actual.getDescription() != null && !actual.getDescription().isEmpty()) {
            failWithMessage("Expected action to have no description but was %s", actual.getDescription());
        }
        return this;
    }

    public ActionInstanceAssert hasEvent(final EventDefinition event) {
        isNotNull();
        if (!Objects.equals(actual.getEvent(), event)) {
            failWithMessage("Expected action to have event %s but was %s",
                    event.toString(), actual.getEvent().toString());
        }
        return this;
    }

    public ActionInstanceAssert hasNoEvent() {
        isNotNull();
        if (actual.getEvent() != null) {
            failWithMessage("Expected action to have no event but was %s", actual.getEvent().toString());
        }
        return this;
    }
}
