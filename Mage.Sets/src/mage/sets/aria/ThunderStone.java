// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ThunderStone extends CardImpl {

    public ThunderStone(UUID ownerId) {
        super(ownerId, 217, "Thunder Stone", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/JjJNfd6.jpg
        R, Sacrifice Thunder Stone: Target creature gets +3/-2 until end of turn.
        */
    }

    public ThunderStone(final ThunderStone card) {
        super(card);
    }

    @Override
    public ThunderStone copy() {
        return new ThunderStone(this);
    }

}

