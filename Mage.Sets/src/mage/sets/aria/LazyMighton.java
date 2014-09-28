// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LazyMighton extends CardImpl {

    public LazyMighton(UUID ownerId) {
        super(ownerId, 21, "Lazy Mighton", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Giant");
        this.subtype.add("Soldier");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/8loRd3l.jpg
        Lazy Mighton can't attack or block unless you've cast an instant or sorcery this turn.
        */
    }

    public LazyMighton(final LazyMighton card) {
        super(card);
    }

    @Override
    public LazyMighton copy() {
        return new LazyMighton(this);
    }

}

