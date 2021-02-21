package fr.chaffotm.gamebook.elementary.model.io;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum IOIndicationType {

    @JsonProperty("clue")
    CLUE,
    @JsonProperty("decision")
    DECISION,
    @JsonProperty("deduction")
    DEDUCTION,
    @JsonProperty("event")
    EVENT;
}
