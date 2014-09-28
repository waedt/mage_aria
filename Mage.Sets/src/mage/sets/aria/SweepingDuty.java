// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SweepingDuty extends CardImpl {

    public SweepingDuty(UUID ownerId) {
        super(ownerId, 40, "Sweeping Duty", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/G4HUbyu.jpg
        You may sacrifice a white creature rather than pay Sweeping Duty's mana cost.
        Destroy all enchantments.
        */
    }

    public SweepingDuty(final SweepingDuty card) {
        super(card);
    }

    @Override
    public SweepingDuty copy() {
        return new SweepingDuty(this);
    }

}

