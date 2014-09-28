// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WillUntoDarkness extends CardImpl {

    public WillUntoDarkness(UUID ownerId) {
        super(ownerId, 120, "Will Unto Darkness", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{B}{B}{B}{B}{B}{B}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/C9vVKVH.jpg
        Each opponent loses 7 life and sacrifices seven permanents.
        */
    }

    public WillUntoDarkness(final WillUntoDarkness card) {
        super(card);
    }

    @Override
    public WillUntoDarkness copy() {
        return new WillUntoDarkness(this);
    }

}

