package fr.chaffotm.gamebook.elementary.service.expression;

import fr.chaffotm.gamebook.elementary.model.entity.CharacterEntity;
import fr.chaffotm.gamebook.elementary.model.entity.SkillEntity;
import fr.chaffotm.gamebook.elementary.model.instance.Indication;
import fr.chaffotm.gamebook.elementary.model.instance.IndicationType;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

public class ExpressionEvaluator {

    public boolean evaluateIndications(final String expression, final GameContext context, final Object value) {
        if (expression == null || expression.isBlank()) {
            return true;
        }
        final Map<String, Object> variables = new HashMap<>();
        for (IndicationType type : IndicationType.values()) {
            variables.put(type.toString(), new ExistenceMap<String, Boolean>());
        }
        for (Indication indication : context.getIndications()) {
            final Map<String, Boolean> type = (Map<String, Boolean>) variables.get(indication.getType().toString());
            type.put(indication.getValue(), true);
        }
        if (value != null) {
            variables.put("value", value);
        }
        return MVEL.eval(expression, variables, Boolean.class);
    }

    public int evaluateSkills(final String expression, final GameContext context) {
        final Map<String, Object> variables = new HashMap<>();
        final CharacterEntity character = context.getCharacter();
        final SkillMap<String, Integer> skills = new SkillMap<>();
        for (SkillEntity skill : character.getSkills()) {
            skills.put(skill.getName(), skill.getValue());
        }
        variables.put("skill", skills);
        variables.put("die", context.getDie());
        return MVEL.eval(expression, variables, Integer.class);
    }

}
