package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.service.Die;
import fr.chaffotm.gamebook.elementary.model.instance.ActionInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.List;

public class TestActionDefinition implements ActionDefinition {

    private final Bonus bonus;

    private final List<Option> options;

    public TestActionDefinition(final List<Option> options) {
        this(options, null);
    }

    public TestActionDefinition(final List<Option> options, final Bonus bonus) {
        this.bonus = bonus;
        this.options = options;
    }

    @Override
    public ActionInstance toInstance(final GameContext context) {
        final int value = bonus == null ? 0 : bonus.getValue(context);
        final Die die = context.getDie();
        final int result = die.roll() + value;
        for (Option option : options) {
            if (result <= option.getUpperLimit()) {
                return new ActionInstance(option.getNextId());
            }
        }
        return null;
    }

}
