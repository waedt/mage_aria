package mage.sets.aria;

import java.util.UUID;

public class GargoyleCastle extends mage.sets.magic2010.GargoyleCastle {

    public GargoyleCastle(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 220;
        this.expansionSetCode = "ARI";

        // TODO: Is the rarity the same as in M10?

        /*
        Card Text:
        ----------
        http://41.media.tumblr.com/5acd5857b059ab0892ea7d50498b991f/tumblr_n60d97vGVL1tcy430o8_400.png
        T: Add 1 to your mana pool.
        5, T, Sacrifice Gargoyle Castle: Put a 3/4 colorless Gargoyle artifact creature token with flying onto the battlefield.
        */
    }

    public GargoyleCastle(final GargoyleCastle card) {
        super(card);
    }

    @Override
    public GargoyleCastle copy() {
        return new GargoyleCastle(this);
    }

}

