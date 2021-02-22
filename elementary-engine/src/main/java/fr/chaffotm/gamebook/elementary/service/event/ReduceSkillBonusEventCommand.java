package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ParameterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.CharacterInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.SkillInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;

import java.util.Set;

public class ReduceSkillBonusEventCommand implements EventCommand {

    @Override
    public void execute(final GameContext context, final Set<ParameterDefinition> parameters) {
        if (parameters.size() != 1) {
            throw new IllegalArgumentException("Only one reduction is possible");
        }
        final ParameterDefinition skillParameter = parameters.iterator().next();

        CharacterInstance character = context.getCharacter();
        SkillInstance skillInstance = character.getSkills().stream()
                .filter(skill -> skill.getName().equals(skillParameter.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Character has no skill named:" + skillParameter.getName()));

        int value = skillInstance.getValue() - Integer.parseInt(skillParameter.getValue());
        if (value < 1) {
            skillInstance.setValue(-2);
        } else {
            skillInstance.setValue(value);
        }
    }
}
