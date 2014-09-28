// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class TotalVengeance extends CardImpl {

    public TotalVengeance(UUID ownerId) {
        super(ownerId, 117, "Total Vengeance", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Kbq6od4.jpg
        Destroy each creature that shares a type or converted mana cost with target creature. (This includes the target creature.)
        */
    }

    public TotalVengeance(final TotalVengeance card) {
        super(card);
    }

    @Override
    public TotalVengeance copy() {
        return new TotalVengeance(this);
    }

}

