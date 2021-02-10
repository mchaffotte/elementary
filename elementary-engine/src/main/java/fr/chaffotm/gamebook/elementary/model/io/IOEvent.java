package fr.chaffotm.gamebook.elementary.model.io;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.chaffotm.gamebook.elementary.model.entity.definition.EventDefinition;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IOAddClueEvent.class, name = "addClue"),
        @JsonSubTypes.Type(value = IOAddDecisionEvent.class, name = "addDecision"),
})
public interface IOEvent {
    EventDefinition toEventDefinition();
}
