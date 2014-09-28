// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class HastyDeath extends CardImpl {

    public HastyDeath(UUID ownerId) {
        super(ownerId, 97, "Hasty Death", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/xothMwa.jpg
        Hasty Death costs 4 less to cast if it targets a creature with haste.
        Destroy target nonblack creature.
        */
    }

    public HastyDeath(final HastyDeath card) {
        super(card);
    }

    @Override
    public HastyDeath copy() {
        return new HastyDeath(this);
    }

}

