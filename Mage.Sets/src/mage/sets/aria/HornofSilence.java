// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class HornofSilence extends CardImpl {

    public HornofSilence(UUID ownerId) {
        super(ownerId, 210, "Horn of Silence", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Equipment");

        /*
        Card Text:
        ----------
        http://i.imgur.com/8qXvwvs.jpg
        Whenever equipped creature attacks, defending player can't cast spells this turn.
        Equip 0
        */
    }

    public HornofSilence(final HornofSilence card) {
        super(card);
    }

    @Override
    public HornofSilence copy() {
        return new HornofSilence(this);
    }

}

