package fr.chaffotm.gamebook.elementary.service.expression;

import fr.chaffotm.gamebook.elementary.model.entity.instance.CharacterInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationInstance;
import fr.chaffotm.gamebook.elementary.model.entity.instance.IndicationType;
import fr.chaffotm.gamebook.elementary.model.entity.instance.SkillInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import fr.chaffotm.gamebook.elementary.service.OldBritishMoneyConverter;

import java.util.HashMap;
import java.util.Map;

public class VariableBuilder {

    private final GameContext context;

    private final Map<String, Object> variables;

    public VariableBuilder(final GameContext context) {
        this.context = context;
        variables = new HashMap<>();
    }

    public VariableBuilder die() {
        variables.put("die", context.getDie());
        return this;
    }

    public VariableBuilder skills() {
        final CharacterInstance character = context.getCharacter();
        final SkillMap<String, Integer> skills = new SkillMap<>();
        for (SkillInstance skill : character.getSkills()) {
            skills.put(skill.getName(), skill.getValue());
        }
        variables.put("skill", skills);
        return this;
    }

    public VariableBuilder indications() {
        for (IndicationType type : IndicationType.values()) {
            variables.put(type.toString(), new ExistenceMap<String, Boolean>());
        }
        for (IndicationInstance indication : context.getIndications()) {
            final Map<String, Boolean> type = (Map<String, Boolean>) variables.get(indication.getType().toString());
            type.put(indication.getValue(), true);
        }
        return this;
    }

    public VariableBuilder money() {
        final MoneyEvaluator moneyEvaluator = new MoneyEvaluator(new OldBritishMoneyConverter(), context.getCharacter());
        variables.put("character", moneyEvaluator);
        return this;
    }

    public VariableBuilder answer(final String answer) {
        variables.put("answer", answer);
        return this;
    }

    public VariableBuilder value(final Object value) {
        variables.put("value", value);
        return this;
    }

    Map<String, Object> toVariables() {
        return variables;
    }
}
