package fr.chaffotm.gamebook.elementary.model;

import java.util.List;

public class TestActionDefinition implements ActionDefinition {

    private final List<Option> options;

    public TestActionDefinition(final List<Option> options) {
        this.options = options;
    }

    @Override
    public ActionInstance toInstance(final GameContext context) {
        final Die die = context.getDie();
        final int result = die.roll();
        for (Option option : options) {
            if (result <= option.getUpperLimit()) {
                return new ActionInstance(option.getNextId());
            }
        }
        return null;
    }

}
