// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WondermentPeddler extends CardImpl {

    public WondermentPeddler(UUID ownerId) {
        super(ownerId, 164, "Wonderment Peddler", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/bpXxJFb.jpg
        T: Draw two cards, then discard two cards.
        */
    }

    public WondermentPeddler(final WondermentPeddler card) {
        super(card);
    }

    @Override
    public WondermentPeddler copy() {
        return new WondermentPeddler(this);
    }

}

