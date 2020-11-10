package fr.chaffotm.gamebook.elementary.repository;

import fr.chaffotm.gamebook.elementary.model.builder.*;
import fr.chaffotm.gamebook.elementary.model.entity.StoryEntity;

public class StoryMaker {

    public StoryEntity buildDefault() {
        return new StoryBuilder("Trapped")
                .character(new CharacterBuilder("John Doe")
                        .skill("intuition", 1)
                        .skill("athletics", 1)
                        .build())
                .prologue(new SectionBuilder(0)
                        .paragraph("You are locked in a room.")
                        .paragraph("The only visible exit is the door.")
                        .action(new ActionBuilder("If you want to open the door", 254)
                                .build())
                        .action(new ActionBuilder("Or inspect the shelves", 191)
                                .build())
                        .build())
                .section(new SectionBuilder(254)
                        .paragraph("The door is locked. You tried to break down the door.")
                        .action(new ActionBuilder(
                                new OptionBuilder(10)
                                        .expression("clue.A")
                                        .build())
                                .build())
                        .action(new ActionBuilder(
                                new OptionBuilder(12).expression("value < 14").build())
                                .option(new OptionBuilder(444).expression("value < 20").build())
                                .expression("die.roll() + skill.athletics")
                                .build())
                        .build())
                .section(new SectionBuilder(191)
                        .paragraph("Between two books, you find a map showing how to open the door.")
                        .event(new EventBuilder("add-indication")
                                .parameter("clue", "A")
                                .build())
                        .action(new ActionBuilder(254)
                                .build())
                        .build())
                .section(new SectionBuilder(10)
                        .paragraph("You are out.")
                        .build())
                .section(new SectionBuilder(12)
                        .paragraph("You cannot open the door.")
                        .action(new ActionBuilder(191).build())
                        .build())
                .build();
    }

}
