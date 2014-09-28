// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WheelofPain extends CardImpl {

    public WheelofPain(UUID ownerId) {
        super(ownerId, 119, "Wheel of Pain", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/OCHbMVa.jpg
        Buyback 3 (You may pay an additional 3 as you cast this spell. If you do, put this card into your hand as it resolves.)
        Target creature gets -1/-1 until end of turn.
        */
    }

    public WheelofPain(final WheelofPain card) {
        super(card);
    }

    @Override
    public WheelofPain copy() {
        return new WheelofPain(this);
    }

}

