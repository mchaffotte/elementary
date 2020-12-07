package fr.chaffotm.gamebook.elementary.model.io;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.chaffotm.gamebook.elementary.model.entity.definition.ActionDefinition;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IOSingleAction.class, name = "singleOption"),
        @JsonSubTypes.Type(value = IOExpressionAction.class, name = "expressionOption"),
        @JsonSubTypes.Type(value = IOMultivaluedExpressionAction.class, name = "multivaluedExpressionOption"),
})
public interface IOAction {

    ActionDefinition toActionDefinition();
}
