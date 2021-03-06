package fr.chaffotm.gamebook.elementary.service.event;

public class EventFactory {

    public EventCommand getEvent(final String type) {
        if (type.equals("add-indication")) {
            return new AddIndicationEventCommand();
        }
        if (type.equals("reduce-skill-bonus")) {
            return new ReduceSkillBonusEventCommand();
        }
        throw new IllegalArgumentException("Unknown event type: " + type);
    }

}
