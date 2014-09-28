// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MercyOverflowing extends CardImpl {

    public MercyOverflowing(UUID ownerId) {
        super(ownerId, 25, "Mercy Overflowing", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/pxSaGI4.jpg
        Gain 2 life.
        Storm (When you cast this spell, copy it for each spell cast before it this turn.)
        */
    }

    public MercyOverflowing(final MercyOverflowing card) {
        super(card);
    }

    @Override
    public MercyOverflowing copy() {
        return new MercyOverflowing(this);
    }

}

