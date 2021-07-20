package fr.chaffotm.gamebook.elementary.service.event;

public class EventFactory {

    public EventCommand getEvent(final String type) {
        switch (type) {
            case "add-indication":
                return new AddIndicationEventCommand();
            case "reduce-skill-bonus":
                return new ReduceSkillBonusEventCommand();
            case "pay":
                return new PayEventCommand();
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
}
