// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Reconsider extends CardImpl {

    public Reconsider(UUID ownerId) {
        super(ownerId, 68, "Reconsider", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/U9f6f1z.jpg
        Buyback-Exile another spell you control.
        Return target spell to its owner's hand.
        */
    }

    public Reconsider(final Reconsider card) {
        super(card);
    }

    @Override
    public Reconsider copy() {
        return new Reconsider(this);
    }

}

