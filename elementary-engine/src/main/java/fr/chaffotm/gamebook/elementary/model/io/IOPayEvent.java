package fr.chaffotm.gamebook.elementary.model.io;

import fr.chaffotm.gamebook.elementary.model.builder.EventDefinitionBuilder;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;

public class IOPayEvent implements IOEvent {

    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public EventDefinition toEventDefinition() {
        return new EventDefinitionBuilder("pay")
                .parameter("amount", amount)
                .build();
    }
}
