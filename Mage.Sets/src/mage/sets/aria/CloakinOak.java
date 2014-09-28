// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CloakinOak extends CardImpl {

    public CloakinOak(UUID ownerId) {
        super(ownerId, 167, "Cloak in Oak", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/W6yK275.jpg
        Target creature gets +3/+3 and gains hexproof until end of turn.
        */
    }

    public CloakinOak(final CloakinOak card) {
        super(card);
    }

    @Override
    public CloakinOak copy() {
        return new CloakinOak(this);
    }

}

