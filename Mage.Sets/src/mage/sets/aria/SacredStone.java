// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SacredStone extends CardImpl {

    public SacredStone(UUID ownerId) {
        super(ownerId, 214, "Sacred Stone", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/ZEhzZqK.jpg
        W, Sacrifice Sacred Stone: Target creature gains first strike, lifelink, and vigilance until end of turn.
        */
    }

    public SacredStone(final SacredStone card) {
        super(card);
    }

    @Override
    public SacredStone copy() {
        return new SacredStone(this);
    }

}

