// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MastersMagnificence extends CardImpl {

    public MastersMagnificence(UUID ownerId) {
        super(ownerId, 24, "Master's Magnificence", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/PJrjxdt.jpg
        White creatures you control get +1/+1 for each enchantment you control.
        */
    }

    public MastersMagnificence(final MastersMagnificence card) {
        super(card);
    }

    @Override
    public MastersMagnificence copy() {
        return new MastersMagnificence(this);
    }

}

