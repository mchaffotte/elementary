package fr.chaffotm.gamebook.elementary.service.expression;

import fr.chaffotm.gamebook.elementary.model.entity.instance.CharacterInstance;
import fr.chaffotm.gamebook.elementary.service.MoneyConverter;

public class MoneyEvaluator {

    private final MoneyConverter moneyConverter;

    private final CharacterInstance character;

    public MoneyEvaluator(final MoneyConverter moneyConverter, final CharacterInstance character) {
        this.moneyConverter = moneyConverter;
        this.character = character;
    }

    public boolean canPay(final String amount) {
        final int totalAmount = moneyConverter.toSubunit(amount);
        return character.getMoney() - totalAmount >= 0;
    }
}
