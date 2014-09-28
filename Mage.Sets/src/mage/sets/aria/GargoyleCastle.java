// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GargoyleCastle extends CardImpl {

    public GargoyleCastle(UUID ownerId) {
        super(ownerId, 220, "Gargoyle Castle", Rarity.COMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/xyy3Zue.jpg
        T: Add 1 to your mana pool.
        5, T, Sacrifice Gargoyle Castle: Put a 3/4 colorless Gargoyle artifact creature token with flying onto the battlefield.
        */
    }

    public GargoyleCastle(final GargoyleCastle card) {
        super(card);
    }

    @Override
    public GargoyleCastle copy() {
        return new GargoyleCastle(this);
    }

}

