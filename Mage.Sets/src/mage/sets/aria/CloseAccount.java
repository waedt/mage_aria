// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CloseAccount extends CardImpl {

    public CloseAccount(UUID ownerId) {
        super(ownerId, 48, "Close Account", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/B6ZavSv.jpg
        Buyback 3 (You may pay an additional 3 as you cast this spell. If you do, put this card into your hand as it resolves.)
        Return target permanent you control to its owner's hand.
        */
    }

    public CloseAccount(final CloseAccount card) {
        super(card);
    }

    @Override
    public CloseAccount copy() {
        return new CloseAccount(this);
    }

}

