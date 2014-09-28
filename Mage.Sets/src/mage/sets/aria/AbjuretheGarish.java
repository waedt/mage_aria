// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class AbjuretheGarish extends CardImpl {

    public AbjuretheGarish(UUID ownerId) {
        super(ownerId, 42, "Abjure the Garish", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/g4Kf4oj.jpg
        Counter target creature or planeswalker spell.
        */
    }

    public AbjuretheGarish(final AbjuretheGarish card) {
        super(card);
    }

    @Override
    public AbjuretheGarish copy() {
        return new AbjuretheGarish(this);
    }

}

