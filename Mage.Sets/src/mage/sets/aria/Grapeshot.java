package mage.sets.aria;

import java.util.UUID;

public class Grapeshot extends mage.sets.timespiral.Grapeshot {

    public Grapeshot(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 136;
        this.expansionSetCode = "ARI";

        /*
        Card Text:
        ----------
        http://i.imgur.com/a97LNpt.jpg
        Grapeshot deals 1 damage to target creature or player.
        Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        */
    }

    public Grapeshot(final Grapeshot card) {
        super(card);
    }

    @Override
    public Grapeshot copy() {
        return new Grapeshot(this);
    }

}

