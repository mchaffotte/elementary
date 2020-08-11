package fr.chaffotm.gamebook.elementary.model;

import java.util.Optional;

public class SkillBonus implements Bonus {

    private final String name;

    public SkillBonus(final String name) {
        this.name = name;
    }

    @Override
    public int getValue(final GameContext context) {
        final Optional<Skill> optionalSkill = context.getCharacter().getSkill(name);
        if (optionalSkill.isEmpty()) {
            throw new IllegalStateException("Skill \"" + name + "\" does not exist.");
        }
        return optionalSkill.get().getValue();
    }

}
