// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PrimalMight extends CardImpl {

    public PrimalMight(UUID ownerId) {
        super(ownerId, 189, "Primal Might", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/z0o3Urv.jpg
        Target creature gets +1/+1 until end of turn.
        Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        */
    }

    public PrimalMight(final PrimalMight card) {
        super(card);
    }

    @Override
    public PrimalMight copy() {
        return new PrimalMight(this);
    }

}

