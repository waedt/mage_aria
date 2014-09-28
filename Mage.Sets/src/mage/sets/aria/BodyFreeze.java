// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BodyFreeze extends CardImpl {

    public BodyFreeze(UUID ownerId) {
        super(ownerId, 46, "Body Freeze", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/zmMugad.jpg
        Tap target permanent. It doesn't untap during its controller's next untap step.
        Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        */
    }

    public BodyFreeze(final BodyFreeze card) {
        super(card);
    }

    @Override
    public BodyFreeze copy() {
        return new BodyFreeze(this);
    }

}

