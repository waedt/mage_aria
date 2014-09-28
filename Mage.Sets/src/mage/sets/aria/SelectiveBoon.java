// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SelectiveBoon extends CardImpl {

    public SelectiveBoon(UUID ownerId) {
        super(ownerId, 195, "Selective Boon", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/14ZvBSS.jpg
        Buyback-Put a creature you control on top of its owner's library.
        Target creature gets +2/+2 until end of turn.
        */
    }

    public SelectiveBoon(final SelectiveBoon card) {
        super(card);
    }

    @Override
    public SelectiveBoon copy() {
        return new SelectiveBoon(this);
    }

}

