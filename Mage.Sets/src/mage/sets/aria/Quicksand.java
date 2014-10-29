package mage.sets.aria;

import java.util.UUID;

public class Quicksand extends mage.sets.tenth.Quicksand {

    public Quicksand(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 223;
        this.expansionSetCode = "ARI";

        // TODO Verify this should have the same rarity as it does in the
        //      original printing.

        /*
        Card Text:
        ----------
        http://i.imgur.com/vDqi79t.jpg
        T: Add 1 to your mana pool.
        T, Sacrifice Quicksand: Target attacking creature without flying gets -1/-2 until end of turn.
        */
    }

    public Quicksand(final Quicksand card) {
        super(card);
    }

    @Override
    public Quicksand copy() {
        return new Quicksand(this);
    }

}

