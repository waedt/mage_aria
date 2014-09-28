// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ManicSmash extends CardImpl {

    public ManicSmash(UUID ownerId) {
        super(ownerId, 141, "Manic Smash", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/sIVxiID.jpg
        Destroy target artifact.
        Draw a card.
        */
    }

    public ManicSmash(final ManicSmash card) {
        super(card);
    }

    @Override
    public ManicSmash copy() {
        return new ManicSmash(this);
    }

}

