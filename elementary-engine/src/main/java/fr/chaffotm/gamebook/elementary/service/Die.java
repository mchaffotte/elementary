package fr.chaffotm.gamebook.elementary.service;

import java.util.Random;

public class Die {

    private final int numberOfFaces;

    private final Random random;

    public Die() {
        this(6);
    }

    public Die(final int numberOfFaces) {
        random = new Random();
        this.numberOfFaces = numberOfFaces;
    }

    public int roll() {
        return random.nextInt(numberOfFaces);
    }

}