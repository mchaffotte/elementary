package fr.chaffotm.gamebook.elementary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class OldBritishMoneyConverterTest {

    @Test
    @DisplayName("toSubunit should convert to pence with a £sd amount")
    public void toSubunitShouldConvertToPenceWithLSDAmount() {
        final MoneyConverter converter = new OldBritishMoneyConverter();

        int pence = converter.toSubunit("£21 15s 11d");

        assertThat(pence).isEqualTo(5231);
    }

    @ParameterizedTest
    @ValueSource(strings = {"£2145454 15s 11d", "£21 15s  11d", "£21 154545s 11d", "£21 15s 101d", " £21 15s 11d", "£21  15s 11d", "£21 15s 11d "})
    @DisplayName("toSubunit should throw an exception if pattern is wrong")
    public void toSmallestMoneyAmountShould(String pattern) {
        OldBritishMoneyConverter converter = new OldBritishMoneyConverter();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> converter.toSubunit(pattern))
                .withMessage("Amount does not follow syntax: £21 15s 11d");
    }

    @Test
    @DisplayName("toSubunit should convert to pence with a £ amount")
    public void toSubunitShouldConvertToPenceWithLAmount() {
        OldBritishMoneyConverter converter = new OldBritishMoneyConverter();

        int pence = converter.toSubunit("£21");

        assertThat(pence).isEqualTo(5040);
    }

    @Test
    @DisplayName("toSubunit should convert to pence with a s amount")
    public void toSubunitShouldConvertToPenceWithSAmount() {
        OldBritishMoneyConverter converter = new OldBritishMoneyConverter();

        int pence = converter.toSubunit("5s");

        assertThat(pence).isEqualTo(60);
    }

    @Test
    @DisplayName("toSubunit should convert to pence with a d amount")
    public void toSubunitShouldConvertToPenceWithDAmount() {
        OldBritishMoneyConverter converter = new OldBritishMoneyConverter();

        int pence = converter.toSubunit("5d");

        assertThat(pence).isEqualTo(5);
    }

    @Test
    @DisplayName("toSubunit should convert to pence with a £d amount")
    public void toSubunitShouldConvertToPenceWithLDAmount() {
        OldBritishMoneyConverter converter = new OldBritishMoneyConverter();

        int pence = converter.toSubunit("£21 5d");

        assertThat(pence).isEqualTo(5045);
    }
}
