package fr.chaffotm.gamebook.elementary.service;

import fr.chaffotm.gamebook.elementary.model.builder.*;

public class StoryMaker {

    public StoryContext buildDefault() {
        return new StoryDefinitionBuilder("Trapped")
                .character(new CharacterDefinitionBuilder("John Doe")
                        .skill("intuition", 1)
                        .skill("athletics", 1)
                        .build())
                .prologue(new SectionDefinitionBuilder(0)
                        .paragraph("You are locked in a room.")
                        .paragraph("The only visible exit is the door.")
                        .action(new ActionDefinitionBuilder("If you want to open the door", 254)
                                .build())
                        .action(new ActionDefinitionBuilder("Or inspect the shelves", 191)
                                .build())
                        .build())
                .section(new SectionDefinitionBuilder(254)
                        .paragraph("The door is locked. You tried to break down the door.")
                        .action(new ActionDefinitionBuilder(
                                new OptionDefinitionBuilder(10)
                                        .expression("clue.A")
                                        .build())
                                .build())
                        .action(new ActionDefinitionBuilder(
                                new OptionDefinitionBuilder(12).expression("value < 14").build())
                                .option(new OptionDefinitionBuilder(444).expression("value < 20").build())
                                .expression("die.roll() + skill.athletics")
                                .build())
                        .build())
                .section(new SectionDefinitionBuilder(191)
                        .paragraph("Between two books, you find a map showing how to open the door.")
                        .event(new EventDefinitionBuilder("add-indication")
                                .parameter("clue", "A")
                                .build())
                        .action(new ActionDefinitionBuilder(254)
                                .build())
                        .build())
                .section(new SectionDefinitionBuilder(10)
                        .paragraph("You are out.")
                        .build())
                .section(new SectionDefinitionBuilder(12)
                        .paragraph("You cannot open the door.")
                        .action(new ActionDefinitionBuilder(191).build())
                        .build())
                .build();
    }

}
