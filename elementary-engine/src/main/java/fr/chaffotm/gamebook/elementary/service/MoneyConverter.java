package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.resource.Money;

public interface MoneyConverter {

    int toSubunit(final String amount);

    Money toMoney(int amount);
}
