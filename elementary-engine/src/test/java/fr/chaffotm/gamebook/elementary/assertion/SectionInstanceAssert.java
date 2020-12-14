package fr.chaffotm.gamebook.elementary.assertion;

import fr.chaffotm.gamebook.elementary.model.entity.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.SectionInstance;
import org.assertj.core.api.AbstractAssert;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SectionInstanceAssert extends AbstractAssert<SectionInstanceAssert, SectionInstance> {

    public static SectionInstanceAssert assertThat(SectionInstance actual) {
        return new SectionInstanceAssert(actual);
    }

    public SectionInstanceAssert(SectionInstance actual) {
        super(actual, SectionInstanceAssert.class);
    }

    public SectionInstanceAssert hasReference(int reference) {
        isNotNull();
        if (actual.getReference() != reference) {
            failWithMessage("Expected section to have reference %s but was %s",
                    reference, actual.getReference());
        }
        return this;
    }

    public SectionInstanceAssert hasText(String text) {
        isNotNull();
        if (!Objects.equals(actual.getText(), text)) {
            failWithMessage("Expected section to have text %s but was %s",
                    text, actual.getText());
        }
        return this;
    }

    public SectionInstanceAssert hasNoActions() {
        isNotNull();
        if (!actual.getActions().isEmpty()) {
            failWithMessage("Expected section to have action 0 but was %s",
                    actual.getReference());
        }
        return this;
    }

    public SectionInstanceAssert hasActions(ActionInstance... actions) {
        isNotNull();
        final List<ActionInstance> actualActions = actual.getActions();
        if (actualActions.size() != actions.length) {
            failWithMessage("Expected actions to have size %s but was %s",
                    actions.length, actualActions.size());
        }
        final Iterator<ActionInstance> iterator = actualActions.iterator();
        for (ActionInstance action : actions) {
            final ActionInstance actualAction = iterator.next();
            ActionInstanceAssert.assertThat(actualAction)
                    .hasNextReference(action.getNextReference())
                    .hasDescription(action.getDescription())
                    .hasEvent(action.getEvent());
        }
        return this;
    }
}
