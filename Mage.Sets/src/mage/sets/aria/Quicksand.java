// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Quicksand extends CardImpl {

    public Quicksand(UUID ownerId) {
        super(ownerId, 223, "Quicksand", Rarity.COMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ARI";

        

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

