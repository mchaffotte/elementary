package fr.chaffotm.gamebook.elementary.model;

public interface ActionDefinition {

    ActionInstance toInstance(GameContext context);

}
