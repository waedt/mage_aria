// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Vanquish extends CardImpl {

    public Vanquish(UUID ownerId) {
        super(ownerId, 159, "Vanquish", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{R}{R}{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/CykzzyV.jpg
        Destroy target permanent.
        */
    }

    public Vanquish(final Vanquish card) {
        super(card);
    }

    @Override
    public Vanquish copy() {
        return new Vanquish(this);
    }

}

