// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SimplifyMachines extends CardImpl {

    public SimplifyMachines(UUID ownerId) {
        super(ownerId, 37, "Simplify Machines", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{X}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/YIqwcwk.jpg
        Destroy all artifacts with converted mana cost X.
        */
    }

    public SimplifyMachines(final SimplifyMachines card) {
        super(card);
    }

    @Override
    public SimplifyMachines copy() {
        return new SimplifyMachines(this);
    }

}

