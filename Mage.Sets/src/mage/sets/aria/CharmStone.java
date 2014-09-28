// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CharmStone extends CardImpl {

    public CharmStone(UUID ownerId) {
        super(ownerId, 208, "Charm Stone", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/wq8BnAj.jpg
        U, Sacrifice Charm Stone: Return target creature with power 3 or less to its owner's hand.
        */
    }

    public CharmStone(final CharmStone card) {
        super(card);
    }

    @Override
    public CharmStone copy() {
        return new CharmStone(this);
    }

}

