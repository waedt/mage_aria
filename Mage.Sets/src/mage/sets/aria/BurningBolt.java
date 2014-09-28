// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BurningBolt extends CardImpl {

    public BurningBolt(UUID ownerId) {
        super(ownerId, 126, "Burning Bolt", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/cgZyNPf.jpg
        Choose one - Burning Bolt deals 2 damage to target creature; or Burning Bolt deals 3 damage to target player.
        */
    }

    public BurningBolt(final BurningBolt card) {
        super(card);
    }

    @Override
    public BurningBolt copy() {
        return new BurningBolt(this);
    }

}

