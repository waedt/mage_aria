// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WildOwl extends CardImpl {

    public WildOwl(UUID ownerId) {
        super(ownerId, 163, "Wild Owl", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/iByCOG4.jpg
        Flying
        Wild Owl can't block.
        */
    }

    public WildOwl(final WildOwl card) {
        super(card);
    }

    @Override
    public WildOwl copy() {
        return new WildOwl(this);
    }

}

