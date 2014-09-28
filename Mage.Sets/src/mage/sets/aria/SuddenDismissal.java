// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SuddenDismissal extends CardImpl {

    public SuddenDismissal(UUID ownerId) {
        super(ownerId, 77, "Sudden Dismissal", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Gvil01d.jpg
        Return target permanent to its owner's hand. You may draw a card. If you do, discard a card.
        */
    }

    public SuddenDismissal(final SuddenDismissal card) {
        super(card);
    }

    @Override
    public SuddenDismissal copy() {
        return new SuddenDismissal(this);
    }

}

