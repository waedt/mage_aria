// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LostStoneofAcco extends CardImpl {

    public LostStoneofAcco(UUID ownerId) {
        super(ownerId, 211, "Lost Stone of Acco", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/ruDJvNO.jpg
        1, T: Put the bottom card of your library into your hand, then put Lost Stone of Acco on the bottom of its owner's library.
        */
    }

    public LostStoneofAcco(final LostStoneofAcco card) {
        super(card);
    }

    @Override
    public LostStoneofAcco copy() {
        return new LostStoneofAcco(this);
    }

}

