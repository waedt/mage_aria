// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BlindingJustice extends CardImpl {

    public BlindingJustice(UUID ownerId) {
        super(ownerId, 6, "Blinding Justice", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/eEnzfyi.jpg
        Buyback-tap three untapped white creatures you control
        Target player gains shroud until end of turn.
        */
    }

    public BlindingJustice(final BlindingJustice card) {
        super(card);
    }

    @Override
    public BlindingJustice copy() {
        return new BlindingJustice(this);
    }

}

