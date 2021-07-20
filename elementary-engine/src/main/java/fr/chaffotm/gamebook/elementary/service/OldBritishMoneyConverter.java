package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.resource.Money;

import java.util.regex.Pattern;

public class OldBritishMoneyConverter implements MoneyConverter {

    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(£\\d{1,4})( \\d{1,2}s)?( \\d{1,2}d)?$|^(\\d{1,2}s)( \\d{1,2}d)?$|^\\d{1,2}d$");

    private int toPence(final int pounds, final int shillings, final int pence) {
        return (pounds * 20 + shillings) * 12 + pence;
    }

    private int getPounds(final String amount) {
        if (!amount.startsWith("£")) {
            return 0;
        }
        int poundSeparator = amount.indexOf(' ');
        int toIndex = poundSeparator == -1 ? amount.length() : poundSeparator;
        return Integer.parseInt(amount.substring(1, toIndex));
    }

    private int getShillings(final String amount) {
        int shillingIndex = amount.indexOf('s');
        if (shillingIndex == -1) {
            return 0;
        }
        int poundSeparator = amount.indexOf(' ');
        int fromIndex = poundSeparator == -1 ? 0 : poundSeparator + 1;
        return Integer.parseInt(amount.substring(fromIndex, shillingIndex));
    }

    private int getPence(final String amount) {
        int penceIndex = amount.indexOf('d');
        if (penceIndex == -1) {
            return 0;
        }
        int shillingSeparator = amount.lastIndexOf(' ');
        int fromIndex = shillingSeparator == -1 ? 0 : shillingSeparator + 1;
        return Integer.parseInt(amount.substring(fromIndex, penceIndex));
    }

    @Override
    public int toSubunit(final String amount) {
        if (amount == null || amount.isBlank()) {
            return 0;
        }
        if (!AMOUNT_PATTERN.matcher(amount).matches()) {
            throw new IllegalArgumentException("Amount does not follow syntax: £21 15s 11d");
        }
        final int pounds = getPounds(amount);
        final int shillings = getShillings(amount);
        final int pence = getPence(amount);
        return toPence(pounds, shillings, pence);
    }

    public Money toMoney(final int pence) {
        final int restShillings = pence % 240;
        final Money money = new Money();
        money.setPounds(pence / 240);
        money.setShillings(restShillings / 12);
        money.setPence(restShillings % 12);
        return money;
    }
}
