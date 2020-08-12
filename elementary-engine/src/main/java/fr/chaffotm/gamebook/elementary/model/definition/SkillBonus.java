package fr.chaffotm.gamebook.elementary.model.definition;

import fr.chaffotm.gamebook.elementary.service.GameContext;

public class SkillBonus implements Bonus {

    private final String name;

    public SkillBonus(final String name) {
        this.name = name;
    }

    @Override
    public int getValue(final GameContext context) {
        return context.getSkillValue(name);
    }

}
