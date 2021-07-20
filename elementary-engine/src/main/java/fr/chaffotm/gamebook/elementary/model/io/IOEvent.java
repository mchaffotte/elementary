package fr.chaffotm.gamebook.elementary.model.io;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IOCheckIndicationEvent.class, name = "checkIndication"),
        @JsonSubTypes.Type(value = IOReduceSkillBonusEvent.class, name = "reduceSkillBonus"),
        @JsonSubTypes.Type(value = IOPayEvent.class, name = "pay"),
})
public interface IOEvent {
    EventDefinition toEventDefinition();
}
