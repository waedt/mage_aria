// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SunnyRetreat extends CardImpl {

    public SunnyRetreat(UUID ownerId) {
        super(ownerId, 201, "Sunny Retreat", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/PKOb11h.jpg
        Whenever a land enters the battlefield under your control, you may draw a card.
        Whenever a land you control leaves the battlefield, discard a card.
        */
    }

    public SunnyRetreat(final SunnyRetreat card) {
        super(card);
    }

    @Override
    public SunnyRetreat copy() {
        return new SunnyRetreat(this);
    }

}

