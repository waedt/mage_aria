// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MercuryFountain extends CardImpl {

    public MercuryFountain(UUID ownerId) {
        super(ownerId, 221, "Mercury Fountain", Rarity.COMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/UKQACHE.jpg
        T: Add 1 to your mana pool.
        T: Add one mana to your mana pool of any color a land you control could produce.
        */
    }

    public MercuryFountain(final MercuryFountain card) {
        super(card);
    }

    @Override
    public MercuryFountain copy() {
        return new MercuryFountain(this);
    }

}

