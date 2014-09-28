// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SeethingSeal extends CardImpl {

    public SeethingSeal(UUID ownerId) {
        super(ownerId, 150, "Seething Seal", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/D5PZBO6.jpg
        2R, Sacrifice Seething Seal: Add RRRRR to your mana pool.
        */
    }

    public SeethingSeal(final SeethingSeal card) {
        super(card);
    }

    @Override
    public SeethingSeal copy() {
        return new SeethingSeal(this);
    }

}

