package fr.chaffotm.gamebook.elementary.service.event;

import fr.chaffotm.gamebook.elementary.model.entity.definition.ParameterDefinition;
import fr.chaffotm.gamebook.elementary.model.entity.instance.CharacterInstance;
import fr.chaffotm.gamebook.elementary.service.GameContext;
import fr.chaffotm.gamebook.elementary.service.MoneyConverter;
import fr.chaffotm.gamebook.elementary.service.OldBritishMoneyConverter;

import java.util.Set;

public class PayEventCommand implements EventCommand {

    private final MoneyConverter moneyConverter;

    public PayEventCommand() {
        moneyConverter = new OldBritishMoneyConverter();
    }

    @Override
    public void execute(final GameContext context, final Set<ParameterDefinition> parameters) {
        if (parameters.size() != 1) {
            throw new IllegalArgumentException("Only one payment is possible");
        }
        final ParameterDefinition payParameter = parameters.iterator().next();
        final int amount = moneyConverter.toSubunit(payParameter.getValue());
        final CharacterInstance character = context.getCharacter();
        final int rest = character.getMoney() - amount;
        if (rest < 0) {
            throw new IllegalArgumentException("Not enough money");
        }
        character.setMoney(rest);
    }
}
